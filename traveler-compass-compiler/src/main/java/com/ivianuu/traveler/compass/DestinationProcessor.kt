package com.ivianuu.traveler.compass

import com.google.auto.service.AutoService
import com.ivianuu.traveler.compass.crane.CraneBuilder
import com.ivianuu.traveler.compass.extension.ExtensionBuilder
import com.ivianuu.traveler.compass.map.MapBuilder
import com.ivianuu.traveler.compass.navigator.NavigatorFactoryBuilder
import com.ivianuu.traveler.compass.serializer.SerializerBuilder
import com.ivianuu.traveler.compass.util.serializerClassName
import com.ivianuu.traveler.compass.util.serializerPackageName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
class DestinationProcessor : AbstractProcessor() {

    override fun getSupportedAnnotationTypes() = mutableSetOf(Destination::class.java.name)

    override fun getSupportedSourceVersion() = SourceVersion.latest()!!

    override fun process(set: MutableSet<out TypeElement>, env: RoundEnvironment): Boolean {
        val destinations = env.getElementsAnnotatedWith(Destination::class.java)
        if (destinations.isEmpty()) return false

        destinations
            .mapNotNull { it as? TypeElement }
            .onEach { element -> generateSerializer(element) }
            .onEach { element -> generateExtensions(element) }
            .also { elements -> generateMap(elements) }
            .also { elements -> generateCrane(elements) }
            .also { elements -> generateNavigator(elements) }

        return true
    }


    private fun generateSerializer(base: TypeElement) {
        val packageName = base.serializerPackageName(processingEnv)
        val className = base.serializerClassName()

        val fileUri = processingEnv.filer.createSourceFile(className, base).toUri()

        val type = TypeSpec.objectBuilder(className)
            .let { SerializerBuilder.addToBundleMethod(processingEnv, it, base) }
            .let { SerializerBuilder.addFromBundleMethod(processingEnv, it, base) }
            .build()

        val file = FileSpec.builder(packageName, className)
            .addType(type)
            .build()

        file.writeTo(File(fileUri))
    }

    private fun generateExtensions(base: TypeElement) {
        val packageName = "com.ivianuu.traveler.compass"
        val fileName = "${base.qualifiedName}Ext"
        val fileUri = processingEnv.filer.createSourceFile(fileName, base).toUri()
        val fileSpec = FileSpec.builder(packageName, fileName)

        ExtensionBuilder
            .buildSerializerFunctions(processingEnv, fileSpec, base)
        fileSpec.build().writeTo(File(fileUri))
    }

    private fun generateCrane(elements: List<TypeElement>) {
        val packageName = "com.ivianuu.traveler.compass"
        val fileName = "AutoCrane"
        val fileUri = processingEnv.filer.createSourceFile(fileName, *elements.toTypedArray()).toUri()
        val fileSpec = FileSpec.builder(packageName, fileName)
        CraneBuilder
            .buildAutoCrane(processingEnv, fileSpec, elements)
        fileSpec.build().writeTo(File(fileUri))
    }

    private fun generateMap(elements: List<TypeElement>) {
        val packageName = "com.ivianuu.traveler.compass"
        val fileName = "AutoMap"
        val fileUri = processingEnv.filer.createSourceFile(fileName, *elements.toTypedArray()).toUri()
        val fileSpec = FileSpec.builder(packageName, fileName)
        MapBuilder.buildMapType(processingEnv, fileSpec, elements)
        fileSpec.build().writeTo(File(fileUri))
    }

    private fun generateNavigator(elements: List<TypeElement>) {
        val packageName = "com.ivianuu.traveler.compass"
        val fileName = "NavigatorFactoryExt"
        val fileUri = processingEnv.filer.createSourceFile(fileName, *elements.toTypedArray()).toUri()
        val fileSpec = FileSpec.builder(packageName, fileName)

        NavigatorFactoryBuilder
            .buildNavigatorExtensions(processingEnv, fileSpec)

        fileSpec.build().writeTo(File(fileUri))
    }
}