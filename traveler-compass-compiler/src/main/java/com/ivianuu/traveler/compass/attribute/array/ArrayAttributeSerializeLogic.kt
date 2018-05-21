package com.ivianuu.traveler.compass.attribute.array

import com.ivianuu.traveler.compass.attribute.AbstractAttributeSerializeLogic
import javax.lang.model.element.VariableElement
import javax.lang.model.type.ArrayType
import javax.lang.model.type.TypeKind

abstract class ArrayAttributeSerializeLogic : AbstractAttributeSerializeLogic() {
    val VariableElement.arrayType: ArrayType?
        get() {
            if (this.asType().kind != TypeKind.ARRAY) return null
            return this.asType() as ArrayType
        }
}

