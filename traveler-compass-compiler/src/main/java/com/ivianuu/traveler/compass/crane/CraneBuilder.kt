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

package com.ivianuu.traveler.compass.crane

import com.ivianuu.traveler.compass.util.serializerClassName
import com.ivianuu.traveler.compass.util.serializerPackageName
import com.squareup.kotlinpoet.*
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement

object CraneBuilder {

    fun buildAutoCrane(environment: ProcessingEnvironment, fileSpec: FileSpec.Builder, elements: List<TypeElement>) {
        val classBuilder = TypeSpec.classBuilder("AutoCrane")
            .addSuperinterface(ClassName("com.ivianuu.traveler.compass", "CompassCrane"))

        val bundleFunction = FunSpec.builder("toBundle")
            .addModifiers(KModifier.OVERRIDE)
            .returns(ClassName("android.os", "Bundle").asNullable())
            .addParameter("destination", Any::class)

        elements.forEach { element ->
            fileSpec.addAliasedImport(element.asClassName(), element.asClassName().simpleName())
            fileSpec.addAliasedImport(
                ClassName(element.serializerPackageName(environment),
                    element.serializerClassName()),
                element.serializerClassName())
            bundleFunction.beginControlFlow("if (destination is ${element.simpleName})")
            bundleFunction.addStatement("val bundle = Bundle()")
            bundleFunction.addStatement("${element.serializerClassName()}.writeToBundle(destination, bundle)")
            bundleFunction.addStatement("return bundle")
            bundleFunction.endControlFlow()
        }

        bundleFunction.addStatement("else throw IllegalArgumentException(\"unknown destination \$destination\")")

        classBuilder.addFunction(bundleFunction.build())
        fileSpec.addType(classBuilder.build())

        val autoCraneExtensionFunction = FunSpec.builder("autoCrane")
            .receiver(ClassName("com.ivianuu.traveler.compass", "CompassNavigator.Builder"))
            .returns(ClassName("com.ivianuu.traveler.compass", "CompassNavigator.Builder"))
            .addStatement("addCrane(AutoCrane())")
            .addStatement("return this")

        fileSpec.addFunction(autoCraneExtensionFunction.build())
    }

}