package com.ivianuu.traveler.compass.attribute

import com.ivianuu.traveler.compass.serializer.SerializerBuilder
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.util.ElementFilter

abstract class AbstractAttributeSerializeLogic : AttributeSerializeLogic {
    protected fun createBundlePut(
        builder: FunSpec.Builder,
        attribute: VariableElement,
        baseElement: TypeElement,
        putMethodName: String,
        valueName: String
    ) {
        val statement = SerializerBuilder.PARAM_BUNDLE +
                ".$putMethodName(\"${bundleArgumentName(attribute, baseElement)}\", $valueName)"
        builder.addCode(statement)
    }

    protected fun createBundleGet(
        builder: FunSpec.Builder,
        attribute: VariableElement,
        baseElement: TypeElement,
        getMethodName: String,
        valueName: String,
        castTo: String? = null,
        parameter: String? = null
    ) {
        val statement = "val $valueName = " +
                SerializerBuilder.PARAM_BUNDLE + ".$getMethodName" +
                (if (parameter != null) "<$parameter>" else "") +
                "(\"${bundleArgumentName(attribute, baseElement)}\") " +
                if (castTo != null) "\n as $castTo" else ""

        builder.addStatement(statement)
    }

    protected fun createAccessor(
        environment: ProcessingEnvironment,
        builder: FunSpec.Builder,
        baseElement: TypeElement,
        attribute: VariableElement
    ): String {
        builder.addCode(
            CodeBlock.builder()
                .add(
                    "val ${attribute.simpleName} = ${SerializerBuilder.PARAM_DESTINATION}.${attribute.simpleName}"
                )
                .build()
        )
        builder.addCode("\n")

        return attribute.simpleName.toString()
    }


    private fun throwNoAccessorFound(baseElement: TypeElement, attribute: VariableElement) {
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


    protected fun bundleArgumentName(element: VariableElement, destination: TypeElement) =
            destination.asType().toString() + ".${element.simpleName}"
}