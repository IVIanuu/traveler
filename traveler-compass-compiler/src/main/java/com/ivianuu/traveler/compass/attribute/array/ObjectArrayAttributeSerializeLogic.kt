package com.ivianuu.traveler.compass.attribute.array

import com.squareup.kotlinpoet.FunSpec
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement

abstract class ObjectArrayAttributeSerializeLogic : ArrayAttributeSerializeLogic() {
    abstract val type: String
    open fun putMethodName(type: TypeElement): String =
        "put${type.simpleName.toString().capitalize()}Array"

    open fun getMethodName(type: TypeElement): String =
        "get${type.simpleName.toString().capitalize()}Array"

    override fun addAttributeSerializeLogic(
        environment: ProcessingEnvironment,
        builder: FunSpec.Builder,
        baseElement: TypeElement,
        attribute: VariableElement
    ): Boolean {
        val type = environment.elementUtils.getTypeElement(type)
        val arrayType = attribute.arrayType ?: return false
        if (!environment.typeUtils.isAssignable(
                arrayType.componentType,
                type.asType()
            )) return false

        val accessor = createAccessor(environment, builder, baseElement, attribute)
        createBundlePut(builder, attribute, baseElement, putMethodName(type), accessor)
        return true
    }

    override fun addBundleAccessorLogic(
        environment: ProcessingEnvironment,
        builder: FunSpec.Builder,
        baseElement: TypeElement,
        attribute: VariableElement,
        valueName: String
    ): Boolean {
        val type = environment.elementUtils.getTypeElement(type)

        val arrayType = attribute.arrayType ?: return false
        if (!environment.typeUtils.isAssignable(
                arrayType.componentType,
                type.asType()
            )) return false

        createBundleGet(
            builder,
            attribute,
            baseElement,
            getMethodName(type),
            valueName,
            castTo = if (this is ParcelableArrayAttributeSerializeLogic)
                "Array<${arrayType.componentType}>"
            else
                null
        )

        return true
    }
}

class ParcelableArrayAttributeSerializeLogic : ObjectArrayAttributeSerializeLogic() {
    override val type = "android.os.Parcelable"
}

class StringArrayAttributeSerializeLogic : ObjectArrayAttributeSerializeLogic() {
    override val type = "java.lang.String"
}