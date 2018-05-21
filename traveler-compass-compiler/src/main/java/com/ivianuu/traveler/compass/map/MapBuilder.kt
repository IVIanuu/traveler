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

package com.ivianuu.traveler.compass.map

import com.ivianuu.traveler.compass.util.destinationTarget
import com.squareup.kotlinpoet.*
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement

object MapBuilder {

    fun buildMapType(environment: ProcessingEnvironment, fileSpec: FileSpec.Builder, elements: List<TypeElement>) {
        fileSpec.addStaticImport("com.ivianuu.traveler.compass", "asRoute")
        fileSpec.addAliasedImport(
            ClassName.bestGuess("com.ivianuu.traveler.compass.CompassNavigator"),
            ClassName.bestGuess("com.ivianuu.traveler.compass.CompassNavigator").simpleName()
        )

        val classBuilder = TypeSpec.classBuilder("AutoMap")
            .addSuperinterface(ClassName("com.ivianuu.traveler.compass", "CompassMap"))

        val getFunction = FunSpec.builder("get")
            .addModifiers(KModifier.OVERRIDE)
            .addParameter("destination", Any::class)
            .returns(ClassName("com.ivianuu.traveler.compass", "CompassRoute"))

        getFunction.beginControlFlow("return when(destination)")

        elements
            .mapNotNull { element -> element.destinationTarget?.let { element to it } }
            .forEach { (element, target) ->
                val targetElement = environment.typeUtils.asElement(target) as TypeElement
                fileSpec.addAliasedImport(element.asClassName(), element.asClassName().simpleName())
                fileSpec.addAliasedImport(targetElement.asClassName(), targetElement.asClassName().simpleName())

                getFunction.addStatement("" +
                        "is ${element.asClassName().simpleName()} -> " +
                        targetElement.asClassName().simpleName() +
                        (if (targetElement.isActivity(environment)) "::class" else "()") +
                        ".asRoute()")
            }

        getFunction.addStatement("else -> throw IllegalArgumentException(\"unknown destination \$destination\")")

        getFunction.endControlFlow()

        classBuilder.addFunction(getFunction.build())
        fileSpec.addType(classBuilder.build())

        val autoMapExtensionFunction = FunSpec.builder("autoMap")
            .receiver(ClassName("com.ivianuu.traveler.compass", "CompassNavigator.Builder"))
            .returns(ClassName("com.ivianuu.traveler.compass", "CompassNavigator.Builder"))
            .addStatement("addMap(AutoMap())")
            .addStatement("return this")

        fileSpec.addFunction(autoMapExtensionFunction.build())
    }


    private fun TypeElement.isActivity(environment: ProcessingEnvironment): Boolean {
        val activityType = environment.elementUtils.getTypeElement("android.app.Activity")
        return environment.typeUtils.isAssignable(this.asType(), activityType.asType())
    }

}