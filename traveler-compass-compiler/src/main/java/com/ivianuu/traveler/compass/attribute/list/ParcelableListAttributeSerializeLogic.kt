package com.ivianuu.traveler.compass.attribute.list

import com.ivianuu.traveler.compass.util.CLASS_TYPE_UTIL
import com.squareup.kotlinpoet.FunSpec
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.type.DeclaredType

class ParcelableListAttributeSerializeLogic : ListAttributeSerializeLogic() {
    override fun addAttributeSerializeLogic(
        environment: ProcessingEnvironment,
        builder: FunSpec.Builder,
        baseElement: TypeElement,
        attribute: VariableElement
    ): Boolean {

        val parcelableType =
            environment.elementUtils.getTypeElement("android.os.Parcelable").asType()
        val wildcardParcelableType = environment.typeUtils.getWildcardType(parcelableType, null)

        val wildcardParcelableList = getListType(
            environment,
            wildcardParcelableType
        )


        if (!environment.typeUtils.isAssignable(
                attribute.asType(),
                wildcardParcelableList
            )) return false
        val accessor = createAccessor(environment, builder, baseElement, attribute)
        val accessorArrayList = accessor + "ArrayList"

        builder.addStatement(
            "" +
                    "val $accessorArrayList " +
                    "= %T.toParcelableArrayList($accessor)",
            CLASS_TYPE_UTIL
        )

        createBundlePut(builder, attribute, baseElement, "putParcelableArrayList", accessorArrayList)
        return true
    }

    override fun addBundleAccessorLogic(
        environment: ProcessingEnvironment,
        builder: FunSpec.Builder,
        baseElement: TypeElement,
        attribute: VariableElement,
        valueName: String
    ): Boolean {
        val parcelableType =
            environment.elementUtils.getTypeElement("android.os.Parcelable").asType()
        val wildcardParcelableType = environment.typeUtils.getWildcardType(parcelableType, null)

        val wildcardParcelableList = getListType(
            environment,
            wildcardParcelableType
        )
        if (!environment.typeUtils.isAssignable(
                attribute.asType(),
                wildcardParcelableList
            )) return false

        val attributeTypeParam = (attribute.asType() as DeclaredType).typeArguments[0]

        createBundleGet(builder, attribute, baseElement,  "getParcelableArrayList", valueName,
            parameter = attributeTypeParam.toString())
        return true
    }

}