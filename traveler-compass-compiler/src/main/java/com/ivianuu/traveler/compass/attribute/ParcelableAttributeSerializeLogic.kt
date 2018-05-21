package com.ivianuu.traveler.compass.attribute

import com.squareup.kotlinpoet.FunSpec
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement

class ParcelableAttributeSerializeLogic : AbstractAttributeSerializeLogic() {
    override fun addAttributeSerializeLogic(
        environment: ProcessingEnvironment,
        builder: FunSpec.Builder,
        baseElement: TypeElement,
        attribute: VariableElement
    ): Boolean {
        if (!isParcelableType(environment, attribute)) return false
        val valueName = createAccessor(environment, builder, baseElement, attribute)
        createBundlePut(builder, attribute, baseElement, "putParcelable", valueName)
        return true
    }

    override fun addBundleAccessorLogic(
        environment: ProcessingEnvironment,
        builder: FunSpec.Builder,
        baseElement: TypeElement, attribute: VariableElement,
        valueName: String
    ): Boolean {
        if (!isParcelableType(environment, attribute)) return false
        createBundleGet(builder, attribute, baseElement,  "getParcelable", valueName,
            attribute.asType().toString())
        return true
    }


    private fun isParcelableType(
        environment: ProcessingEnvironment,
        attribute: VariableElement
    ): Boolean {
        val parcelableElement = environment.elementUtils.getTypeElement("android.os.Parcelable")
        return environment.typeUtils.isAssignable(attribute.asType(), parcelableElement.asType())
    }
}

