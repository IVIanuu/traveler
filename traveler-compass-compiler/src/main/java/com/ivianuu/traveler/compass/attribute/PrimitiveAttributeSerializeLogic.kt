package com.ivianuu.traveler.compass.attribute

import com.squareup.kotlinpoet.FunSpec
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.type.TypeKind

class PrimitiveAttributeSerializeLogic : AbstractAttributeSerializeLogic() {
    override fun addAttributeSerializeLogic(
        environment: ProcessingEnvironment,
        builder: FunSpec.Builder,
        baseElement: TypeElement,
        attribute: VariableElement
    ): Boolean {

        val putMethodName = when (attribute.asType().kind) {
            TypeKind.INT -> "putInt"
            TypeKind.LONG -> "putLong"
            TypeKind.DOUBLE -> "putDouble"
            TypeKind.FLOAT -> "putFloat"
            TypeKind.BOOLEAN -> "putBoolean"
            TypeKind.BYTE -> "putByte"
            TypeKind.SHORT -> "putShort"
            TypeKind.CHAR -> "putChar"
            else -> null
        } ?: return false

        val valueName = createAccessor(environment, builder, baseElement, attribute)
        createBundlePut(builder, attribute, baseElement, putMethodName, valueName)
        return true
    }


    override fun addBundleAccessorLogic(
        environment: ProcessingEnvironment,
        builder: FunSpec.Builder,
        baseElement: TypeElement,
        attribute: VariableElement,
        valueName: String
    ): Boolean {
        val getMethodName = when (attribute.asType().kind) {
            TypeKind.INT -> "getInt"
            TypeKind.LONG -> "getLong"
            TypeKind.DOUBLE -> "getDouble"
            TypeKind.FLOAT -> "getFloat"
            TypeKind.BOOLEAN -> "getBoolean"
            TypeKind.BYTE -> "getByte"
            TypeKind.SHORT -> "getShort"
            TypeKind.CHAR -> "getChar"
            else -> null
        } ?: return false

        createBundleGet(builder, attribute, baseElement,  getMethodName, valueName)
        return true
    }


}


