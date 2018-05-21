package com.ivianuu.traveler.compass

import com.google.auto.service.AutoService
import com.ivianuu.traveler.compass.crane.CraneBuilder
import com.ivianuu.traveler.compass.extension.ExtensionBuilder
import com.ivianuu.traveler.compass.map.MapBuilder
import com.ivianuu.traveler.compass.navigator.NavigatorFactoryBuilder
import com.ivianuu.traveler.compass.pilot.PilotBuilder
import com.ivianuu.traveler.compass.serializer.SerializerBuilder
import com.ivianuu.traveler.compass.util.serializerClassName
import com.ivianuu.traveler.compass.util.serializerPackageName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
class CompassProcessor : AbstractProcessor() {

    private var extPackage: String? = null

    override fun init(env: ProcessingEnvironment) {
        super.init(env)
        extPackage = env.options[OPTION_EXT_PACKAGE]
    }

    override fun getSupportedAnnotationTypes() =
        mutableSetOf(Destination::class.java.name, Detour::class.java.name)

    override fun getSupportedSourceVersion() = SourceVersion.latest()!!

    override fun process(set: MutableSet<out TypeElement>, env: RoundEnvironment): Boolean {
        val handledByDestinations = processDestinations(env)
        val handledByDetours = processDetours(env)

        return handledByDestinations || handledByDetours
    }

    private fun processDestinations(env: RoundEnvironment): Boolean {
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

    private fun processDetours(env: RoundEnvironment): Boolean {
        val detours = env.getElementsAnnotatedWith(Detour::class.java)
        if (detours.isEmpty()) return false
        detours.asSequence()
            .mapNotNull { it as TypeElement }
            .toList()
            .also { elements -> generateDetourPilot(elements) }

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
        val packageName = processingEnv.elementUtils.getPackageOf(base).toString()
        val fileName = "${base.qualifiedName}Ext"
        val fileUri = processingEnv.filer.createSourceFile(fileName, base).toUri()
        val fileSpec = FileSpec.builder(packageName, fileName)

        ExtensionBuilder
            .buildSerializerFunctions(processingEnv, fileSpec, base)
        fileSpec.build().writeTo(File(fileUri))
    }

    private fun generateCrane(elements: List<TypeElement>) {
        val packageName = extPackage()
        val fileName = "AutoCrane"
        val fileUri = processingEnv.filer.createSourceFile(fileName, *elements.toTypedArray()).toUri()
        val fileSpec = FileSpec.builder(packageName, fileName)
        CraneBuilder
            .buildAutoCrane(processingEnv, fileSpec, elements)
        fileSpec.build().writeTo(File(fileUri))
    }

    private fun generateMap(elements: List<TypeElement>) {
        val packageName = extPackage()
        val fileName = "AutoMap"
        val fileUri = processingEnv.filer.createSourceFile(fileName, *elements.toTypedArray()).toUri()
        val fileSpec = FileSpec.builder(packageName, fileName)
        MapBuilder.buildMapType(processingEnv, fileSpec, elements)
        fileSpec.build().writeTo(File(fileUri))
    }

    private fun generateNavigator(elements: List<TypeElement>) {
        val packageName = extPackage()
        val fileName = "NavigatorFactoryExt"
        val fileUri = processingEnv.filer.createSourceFile(fileName, *elements.toTypedArray()).toUri()
        val fileSpec = FileSpec.builder(packageName, fileName)

        NavigatorFactoryBuilder
            .buildNavigatorExtensions(processingEnv, fileSpec)

        fileSpec.build().writeTo(File(fileUri))
    }

    private fun generateDetourPilot(elements: List<TypeElement>) {
        val packageName = extPackage()
        val fileName = "AutoDetourPilot"
        val fileUri = processingEnv.filer.createSourceFile(fileName, *elements.toTypedArray()).toUri()
        val fileSpec = FileSpec.builder(packageName, fileName)
        PilotBuilder.buildPilotType(processingEnv, fileSpec, elements)
        fileSpec.build().writeTo(File(fileUri))
    }

    private fun extPackage() = extPackage ?: DEFAULT_EXT_PACKAGE

    companion object {
        const val DEFAULT_EXT_PACKAGE = "com.ivianuu.traveler.compass"
        const val OPTION_EXT_PACKAGE = "ext_package"
    }
}