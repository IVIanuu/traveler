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

package com.ivianuu.traveler.compass.pilot

import com.squareup.kotlinpoet.*
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement

object PilotBuilder {

    fun buildPilotType(environment: ProcessingEnvironment, fileSpec: FileSpec.Builder, elements: List<TypeElement>) {
        val classBuilder = TypeSpec.classBuilder("AutoDetourPilot")
            .superclass(ClassName("com.ivianuu.traveler.compass", "OpenDetourPilot"))

        val initBlock = CodeBlock.builder()
        elements
            .forEach { element ->
                fileSpec.addAliasedImport(element.asClassName(), element.asClassName().simpleName())
                initBlock.addStatement("" +
                        "registerDetour(${element.asClassName().simpleName()}())"
                )
            }


        val autoPilotExtensionFunction = FunSpec.builder("autoPilot")
            .receiver(ClassName("com.ivianuu.traveler.compass", "CompassNavigator.Builder"))
            .returns(ClassName("com.ivianuu.traveler.compass", "CompassNavigator.Builder"))
            .addStatement("addPilot(AutoDetourPilot())")
            .addStatement("return this")

        classBuilder.addInitializerBlock(initBlock.build())
        fileSpec.addType(classBuilder.build())
        fileSpec.addFunction(autoPilotExtensionFunction.build())
    }

}