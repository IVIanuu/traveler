package com.ivianuu.traveler.compass.attribute.boxedprimitives

import com.ivianuu.traveler.compass.attribute.AbstractAttributeSerializeLogic
import com.ivianuu.traveler.compass.serializer.SerializerBuilder
import com.squareup.kotlinpoet.FunSpec
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement

abstract class BoxedPrimitiveAttributeSerializeLogic : AbstractAttributeSerializeLogic() {
    abstract val className: String
    abstract val putMethodName: String
    abstract val getMethodName: String

    override fun addAttributeSerializeLogic(
        environment: ProcessingEnvironment,
        builder: FunSpec.Builder,
        baseElement: TypeElement,
        attribute: VariableElement
    ): Boolean {
        val boxedType = environment.elementUtils.getTypeElement(className)
        if (!environment.typeUtils.isSubtype(attribute.asType(), boxedType.asType())) {
            return false
        }

        val valueName = createAccessor(environment, builder, baseElement, attribute)

        builder.beginControlFlow("if ($valueName != null)")
        createBundlePut(builder, attribute, baseElement, putMethodName, valueName)
        builder.endControlFlow()

        return true
    }

    override fun addBundleAccessorLogic(
        environment: ProcessingEnvironment,
        builder: FunSpec.Builder,
        baseElement: TypeElement,
        attribute: VariableElement,
        valueName: String
    ): Boolean {
        val boxedType = environment.elementUtils.getTypeElement(className)
        if (!environment.typeUtils.isSameType(attribute.asType(), boxedType.asType())) {
            return false
        }

        builder.beginControlFlow(
            "val $valueName = " +
                    "if (${SerializerBuilder.PARAM_BUNDLE}.containsKey(\"${bundleArgumentName(attribute, baseElement)}\"))"
        )
            .addStatement("${SerializerBuilder.PARAM_BUNDLE}.$getMethodName(\"${bundleArgumentName(attribute, baseElement)}\")")
            .nextControlFlow("else")
            .addStatement("null")
            .endControlFlow()
            .addCode("\n")

        return true

    }
}

class BoxedIntegerAttributeSerializeLogic : BoxedPrimitiveAttributeSerializeLogic() {
    override val className: String
        get() = "java.lang.Integer"
    override val putMethodName: String
        get() = "putInt"
    override val getMethodName: String
        get() = "getInt"
}

class BoxedFloatAttributeSerializeLogic : BoxedPrimitiveAttributeSerializeLogic() {
    override val className: String
        get() = "java.lang.Float"
    override val putMethodName: String
        get() = "putFloat"
    override val getMethodName: String
        get() = "getFloat"
}

class BoxedDoubleAttributeSerializeLogic : BoxedPrimitiveAttributeSerializeLogic() {
    override val className: String
        get() = "java.lang.Double"
    override val putMethodName: String
        get() = "putDouble"
    override val getMethodName: String
        get() = "getDouble"

}

class BoxedBooleanAttributeSerializeLogic : BoxedPrimitiveAttributeSerializeLogic() {
    override val className: String
        get() = "java.lang.Boolean"
    override val putMethodName: String
        get() = "putBoolean"
    override val getMethodName: String
        get() = "getBoolean"
}

class BoxedShortAttributeSerializeLogic : BoxedPrimitiveAttributeSerializeLogic() {
    override val className: String
        get() = "java.lang.Short"
    override val putMethodName: String
        get() = "putShort"
    override val getMethodName: String
        get() = "getShort"

}