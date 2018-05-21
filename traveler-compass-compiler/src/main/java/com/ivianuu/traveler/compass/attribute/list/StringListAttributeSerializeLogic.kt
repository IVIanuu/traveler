package com.ivianuu.traveler.compass.attribute.list

import com.ivianuu.traveler.compass.util.CLASS_TYPE_UTIL
import com.squareup.kotlinpoet.FunSpec
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement

class StringListAttributeSerializeLogic : ListAttributeSerializeLogic() {
    override fun addAttributeSerializeLogic(
        environment: ProcessingEnvironment,
        builder: FunSpec.Builder,
        baseElement: TypeElement,
        attribute: VariableElement
    ): Boolean {
        val stringList = getListType(
            environment,
            environment.elementUtils.getTypeElement("java.lang.String").asType()
        )

        if (!environment.typeUtils.isAssignable(attribute.asType(), stringList)) return false
        val accessor = createAccessor(environment, builder, baseElement, attribute)
        val accessorArrayList = accessor + "ArrayList"

        builder.addStatement(
            "val $accessorArrayList = %T.toArrayList($accessor)",
            CLASS_TYPE_UTIL
        )


        createBundlePut(builder, attribute, baseElement, "putStringArrayList", accessorArrayList)
        return true
    }

    override fun addBundleAccessorLogic(
        environment: ProcessingEnvironment,
        builder: FunSpec.Builder,
        baseElement: TypeElement,
        attribute: VariableElement,
        valueName: String
    ): Boolean {
        val stringList = getListType(
            environment,
            environment.elementUtils.getTypeElement("java.lang.String").asType()
        )
        if (!environment.typeUtils.isAssignable(attribute.asType(), stringList)) return false

        createBundleGet(builder, attribute, baseElement,  "getStringArrayList", valueName)
        return true
    }

}