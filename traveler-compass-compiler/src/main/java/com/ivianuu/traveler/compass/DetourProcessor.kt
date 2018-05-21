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

package com.ivianuu.traveler.compass

import com.google.auto.service.AutoService
import com.ivianuu.traveler.compass.pilot.PilotBuilder
import com.squareup.kotlinpoet.FileSpec
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
class DetourProcessor : AbstractProcessor() {
    override fun getSupportedAnnotationTypes() = mutableSetOf(Detour::class.java.name)
    override fun getSupportedSourceVersion() = SourceVersion.latest()!!

    override fun process(set: MutableSet<out TypeElement>, environment: RoundEnvironment): Boolean {
        val detours = environment.getElementsAnnotatedWith(Detour::class.java)
        if (detours.isEmpty()) return false
        detours.asSequence()
            .mapNotNull { it as TypeElement }
            .toList()
            .also { elements -> generateDetourPilot(elements) }

        return true
    }

    private fun generateDetourPilot(elements: List<TypeElement>) {
        val packageName = "com.ivianuu.traveler.compass"
        val fileName = "AutoDetourPilot"
        val fileUri = processingEnv.filer.createSourceFile(fileName, *elements.toTypedArray()).toUri()
        val fileSpec = FileSpec.builder(packageName, fileName)
        PilotBuilder.buildPilotType(processingEnv, fileSpec, elements)
        fileSpec.build().writeTo(File(fileUri))
    }

}