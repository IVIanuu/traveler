/*
 * Copyright 2018 Manuel Wrage
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ivianuu.traveler.compass.util

import com.ivianuu.traveler.compass.CompassConstructor
import javax.lang.model.element.*
import javax.lang.model.util.ElementFilter

object ConstructorSelector {

    fun getKompassConstructor(element: Element): ExecutableElement {
        val constructors = ElementFilter.constructorsIn(element.enclosedElements)
            .filter { it.modifiers.contains(Modifier.PUBLIC) }
            .asSequence()
            .toList()

        if (constructors.isEmpty()) throw Exception(
            "${element.simpleName}: No public constructor found"
        )

        constructors.getAnnotatedConstructor(element)?.let { return it }
        constructors.getLongestSuitableConstructor(element).let { return it }
    }


    private fun List<ExecutableElement>.getAnnotatedConstructor(base: Element): ExecutableElement? {
        val annotatedConstructors = this
            .filter { it.getAnnotation(CompassConstructor::class.java) != null }
            .toList()

        return when {
            annotatedConstructors.isEmpty() -> null
            annotatedConstructors.size == 1 -> annotatedConstructors.first()
            annotatedConstructors.size > 1 -> throw Exception(
                "" +
                        "${base.simpleName} has more than one constructors annotated with " +
                        "'CompassConstructor'."
            )
            else -> null
        }
    }


    private fun List<ExecutableElement>.getLongestSuitableConstructor(base: Element)
            : ExecutableElement {

        val checkedConstructors = this
            .sortedByDescending { it.parameters.size }
            .map { it to it.isSuitable(base) }


        val suitableConstructors = checkedConstructors
            .filter { it.second == null }
            .toList();

        if (suitableConstructors.isNotEmpty()) return suitableConstructors.first().first

        checkedConstructors
            .mapNotNull { it.second }
            .firstOrNull()?.let { throw it }

        throw IllegalStateException(
            "No suitable constructor found. No error message available. " +
                    "This is a bug, for sure!"
        )

    }

    private fun ExecutableElement.isSuitable(base: Element): Throwable? {
        return this.parameters.asSequence()
            .map { it.hasAccessor(base) }
            .firstOrNull()
    }

    private fun VariableElement.hasAccessor(base: Element): Throwable? {
        val containsFieldAccessor = ElementFilter
            .fieldsIn(base.enclosedElements)
            .asSequence()
            .filter { it.modifiers.contains(Modifier.PUBLIC) }
            .filter { it.simpleName == this.simpleName }
            .firstOrNull() != null

        val containsMethodAccessor = ElementFilter
            .methodsIn(base.enclosedElements)
            .asSequence()
            .filter { it.modifiers.contains(Modifier.PUBLIC) }
            .filter { it.returnType.toString() == this.asType().toString() }
            .filter { it.parameters.isEmpty() }
            .filter {
                it.simpleName == this.simpleName
                        || it.simpleName.toString() ==
                        "get${this.simpleName.toString().capitalize()}"
            }
            .firstOrNull() != null

        return if (!containsFieldAccessor && !containsMethodAccessor) {
            getNoAccessorThrowable(
                base as TypeElement,
                this
            )
        } else null
    }

    private fun getNoAccessorThrowable(baseElement: TypeElement, attribute: VariableElement)
            : Throwable {
        val message = "No accessor found for ${attribute.simpleName}:${attribute.asType()}\n" +
                "Fields: ${ElementFilter.fieldsIn(baseElement.enclosedElements)
                    .map {
                        "${it.modifiers.joinToString(" ")} " +
                                "${it.simpleName}"
                    }} \n" +
                "Methods: ${ElementFilter.methodsIn(baseElement.enclosedElements)
                    .map {
                        "${it.modifiers.joinToString(" ")} " +
                                "${it.simpleName}"
                    }}"
        throw Throwable(message)
    }


}