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

package com.ivianuu.traveler.compass.extension

import com.google.auto.common.AnnotationMirrors
import com.google.auto.common.MoreElements
import com.google.common.base.CaseFormat
import com.ivianuu.traveler.compass.Destination
import com.ivianuu.traveler.compass.util.isSubtypeOfType
import com.ivianuu.traveler.compass.util.serializerClassName
import com.squareup.kotlinpoet.*
import com.sun.tools.javac.code.Type
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

object ExtensionBuilder {

    fun buildSerializerFunctions(
        environment: ProcessingEnvironment,
        builder: FileSpec.Builder,
        element: TypeElement
    ) {
        buildBundleExtension(environment, builder, element)
        buildTargetExtension(environment, builder, element)
        buildDestinationToBundleExtension(environment, builder, element)
    }

    private fun buildDestinationToBundleExtension(
        environment: ProcessingEnvironment,
        builder: FileSpec.Builder,
        element: TypeElement
    ) {
        val serializerClass = ClassName(
            environment.elementUtils.getPackageOf(element).toString(),
            element.serializerClassName()
        )

        builder.addFunction(
            FunSpec.builder("toBundle")
                .receiver(element.asClassName())
                .returns(ClassName.bestGuess("android.os.Bundle"))
                .addCode(
                    CodeBlock.builder()
                        .addStatement("val bundle = Bundle()")
                        .addStatement("%T.writeToBundle(this, bundle)", serializerClass)
                        .addStatement("return bundle")
                        .build()
                )
                .build()
        )
    }

    private fun buildBundleExtension(
        environment: ProcessingEnvironment,
        builder: FileSpec.Builder,
        element: TypeElement
    ) {
        val serializerClass =
            ClassName(
                environment.elementUtils.getPackageOf(element).toString(),
                element.serializerClassName()
            )

        builder.addFunction(
            FunSpec.builder("tryAs${element.simpleName}")
                .receiver(ClassName("android.os", "Bundle").asNullable())
                .returns(element.asClassName().asNullable())
                .beginControlFlow("return try")
                .addStatement("%T.readFromBundle(this!!)", serializerClass)
                .endControlFlow()
                .beginControlFlow("catch(e: Throwable)")
                .addStatement("null")
                .endControlFlow()
                .build()
        )


        builder.addFunction(
            FunSpec.builder("as${element.simpleName}")
                .receiver(ClassName("android.os", "Bundle").asNullable())
                .returns(element.asClassName())
                .beginControlFlow("return try")
                .addStatement("%T.readFromBundle(this!!)", serializerClass)
                .endControlFlow()
                .beginControlFlow("catch(e: Throwable)")
                .addStatement("throw IllegalArgumentException(e)")
                .endControlFlow()
                .build()
        )
    }

    private fun buildTargetExtension(
        environment: ProcessingEnvironment,
        builder: FileSpec.Builder,
        element: TypeElement
    ) {
        val annotation =
            MoreElements.getAnnotationMirror(element, Destination::class.java).get()

        val values = AnnotationMirrors.getAnnotationValue(annotation, "target")

        val name = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, element.simpleName.toString())

        val clazz = values.value as Type.ClassType

        when {
            isSubtypeOfType(
                clazz,
                "android.support.v4.app.Fragment"
            ) -> {
                builder.addFunction(
                    FunSpec.builder(name)
                        .receiver(clazz.asTypeName())
                        .returns(element.asClassName())
                        .addCode(
                            CodeBlock.builder()
                                .addStatement("return arguments.as${element.simpleName}()")
                                .build()
                        )
                        .build()
                )
            }
            isSubtypeOfType(
                clazz,
                "android.app.Activity"
            ) -> {
                builder.addFunction(
                    FunSpec.builder(name)
                        .receiver(clazz.asTypeName())
                        .returns(element.asClassName())
                        .addCode(
                            CodeBlock.builder()
                                .addStatement("return intent.extras.as${element.simpleName}()")
                                .build()
                        )
                        .build()
                )
            }
            else -> environment.messager.printMessage(
                Diagnostic.Kind.ERROR,
                "unsupported target type $clazz"
            )
        }
    }
}