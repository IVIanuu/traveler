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

package com.ivianuu.traveler.compass.serializer

import com.ivianuu.traveler.compass.attribute.attributeSerializers
import com.ivianuu.traveler.compass.util.getKompassConstructor
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

object SerializerBuilder {

    const val PARAM_BUNDLE = "bundle"
    const val PARAM_DESTINATION = "destination"

    const val METHOD_NAME_FROM_BUNDLE = "readFromBundle"
    const val METHOD_NAME_TO_BUNDLE = "writeToBundle"

    private val logicParts = attributeSerializers()

    fun addToBundleMethod(
        environment: ProcessingEnvironment,
        builder: TypeSpec.Builder,
        element: TypeElement
    ): TypeSpec.Builder {

        val constructor = element.getKompassConstructor()
        val funBuilder = FunSpec.builder(METHOD_NAME_TO_BUNDLE)
            .addAnnotation(JvmStatic::class)
            .addParameter(PARAM_DESTINATION, element.asClassName())
            .addParameter(
                PARAM_BUNDLE,
                ClassName.bestGuess("android.os.Bundle")
            )

        constructor.parameters.asSequence()
            .forEachIndexed { index, attribute ->
                if (index > 0) funBuilder.addCode("\n\n")
                val handled = logicParts.asSequence()
                    .map {
                        it.addAttributeSerializeLogic(
                            environment, funBuilder,
                            element, attribute
                        )
                    }
                    .filter { it }
                    .toList()
                    .isNotEmpty()

                if (!handled) environment.messager.printMessage(
                    Diagnostic.Kind.ERROR,
                    "unsupported type $attribute"
                )
            }


        return builder.addFunction(funBuilder.build())

    }

    fun addFromBundleMethod(
        environment: ProcessingEnvironment,
        builder: TypeSpec.Builder,
        element: TypeElement
    ): TypeSpec.Builder {
        val kompassConstructor = element.getKompassConstructor()

        val funBuilder = FunSpec.builder(METHOD_NAME_FROM_BUNDLE)
            .addAnnotation(JvmStatic::class)
            .returns(element.asClassName())
            .addParameter(
                PARAM_BUNDLE,
                ClassName.bestGuess("android.os.Bundle")
            )

        val valueNames = mutableListOf<String>()

        kompassConstructor.parameters
            .asSequence()
            .forEach { attribute ->
                val valueName = attribute.simpleName.toString()
                valueNames.add(valueName)
                val handled = logicParts.asSequence()
                    .map {
                        it.addBundleAccessorLogic(
                            environment,
                            funBuilder, element, attribute, valueName
                        )
                    }
                    .filter { it }
                    .toList().isNotEmpty()

                if (!handled) environment.messager.printMessage(
                    Diagnostic.Kind.ERROR,
                    "unsupported type $attribute"
                )
            }

        funBuilder.addCode("\n")
        val constructorStatement = "return %T(${
        kompassConstructor.parameters
            .asSequence()
            .withIndex()
            .map { valueNames[it.index] }
            .joinToString(", ")
        })"
        funBuilder.addStatement(constructorStatement, element.asType())
        return builder.addFunction(funBuilder.build())

    }

}