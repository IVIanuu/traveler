package com.ivianuu.traveler.compass.attribute.list

import com.ivianuu.traveler.compass.attribute.AbstractAttributeSerializeLogic
import com.ivianuu.traveler.compass.serializer.SerializerBuilder
import com.ivianuu.traveler.compass.util.CLASS_TYPE_UTIL
import com.squareup.kotlinpoet.FunSpec
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.TypeMirror

abstract class ListAttributeSerializeLogic : AbstractAttributeSerializeLogic() {

    protected fun getListType(
        environment: ProcessingEnvironment,
        elementType: TypeMirror
    ): DeclaredType {
        val listInterfaceType = environment.elementUtils.getTypeElement("java.util.List")
        return environment.typeUtils.getDeclaredType(listInterfaceType, elementType)
    }

    protected fun VariableElement.isListOfType(
        environment: ProcessingEnvironment,
        elementType: TypeMirror
    ): Boolean {
        val listType = getListType(environment, elementType)
        return environment.typeUtils.isAssignable(this.asType(), listType)
    }
}


abstract class PrimitiveListAttributeSerializeLogic : ListAttributeSerializeLogic() {

    protected abstract val primitive: String
    protected open val capitalizedPrimitive: String get() = primitive.capitalize()
    protected open val boxedPrimitive: String get() = primitive.capitalize()

    override fun addAttributeSerializeLogic(
        environment: ProcessingEnvironment,
        builder: FunSpec.Builder,
        baseElement: TypeElement,
        attribute: VariableElement
    ): Boolean {
        val objectType =
            environment.elementUtils.getTypeElement("java.lang.$boxedPrimitive").asType()

        if (!attribute.isListOfType(environment, objectType)) return false

        val listValueName = createAccessor(environment, builder, baseElement, attribute)
        val arrayValueName = listValueName + "Array"

        val arrayConversionStatement =
            "val $arrayValueName = %T.to${capitalizedPrimitive}Array($listValueName)"
        builder.addStatement(
            arrayConversionStatement,
            CLASS_TYPE_UTIL
        )

        createBundlePut(builder, attribute, baseElement, "put${capitalizedPrimitive}Array", arrayValueName)

        return true
    }

    override fun addBundleAccessorLogic(
        environment: ProcessingEnvironment,
        builder: FunSpec.Builder,
        baseElement: TypeElement,
        attribute: VariableElement,
        valueName: String
    ): Boolean {
        val floatType =
            environment.elementUtils.getTypeElement("java.lang.$boxedPrimitive").asType()

        if (!attribute.isListOfType(environment, floatType)) return false
        val arrayValueName = "${attribute}Array"

        val getIntArrayStatement = "val $arrayValueName = " + SerializerBuilder.PARAM_BUNDLE +
                ".get${capitalizedPrimitive}Array(\"${bundleArgumentName(attribute, baseElement)}\")"
        builder.addStatement(getIntArrayStatement)

        val listConversionStatement = "val $valueName = " +
                "%T.to${boxedPrimitive}List($arrayValueName)"

        builder.addStatement(listConversionStatement,
            CLASS_TYPE_UTIL
        )
        return true
    }


}

class IntListAttributeSerializeLogic : PrimitiveListAttributeSerializeLogic() {
    override val primitive = "int"
    override val boxedPrimitive = "Integer"
}

class FloatListAttributeSerializeLogic : PrimitiveListAttributeSerializeLogic() {
    override val primitive = "float"
}

class DoubleListAttributeSerializeLogic : PrimitiveListAttributeSerializeLogic() {
    override val primitive = "double"
}

class CharListAttributeSerializeLogic : PrimitiveListAttributeSerializeLogic() {
    override val primitive = "char"
    override val boxedPrimitive = "Character"
}

class BooleanListAttributeSerializeLogic : PrimitiveListAttributeSerializeLogic() {
    override val primitive = "boolean"
}

class LongListAttributeSerializeLogic : PrimitiveListAttributeSerializeLogic() {
    override val primitive = "long"
}

class ShortListAttributeSerializeLogic : PrimitiveListAttributeSerializeLogic() {
    override val primitive = "short"
}