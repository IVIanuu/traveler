/*
 * Copyright 2018 Manuel Wrage
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ivianuu.traveler.compass.util

import com.ivianuu.traveler.compass.Destination
import com.squareup.kotlinpoet.ClassName
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.MirroredTypesException
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror

val CLASS_TYPE_UTIL = ClassName.bestGuess("com.ivianuu.traveler.compass.util.TypeUtil")

fun Element.getKompassConstructor(): ExecutableElement {
    return ConstructorSelector.getKompassConstructor(this)
}

fun Element.serializerClassName(): String {
    return "${this.simpleName}Serializer"
}

val Element.destinationTarget: TypeMirror?
    get() {
        try {
            this.getAnnotation(Destination::class.java).target
        } catch (e: MirroredTypesException) {
            return e.typeMirrors.firstOrNull()
        }

        return null
    }



fun Element.serializerPackageName(environment: ProcessingEnvironment): String {
    return environment.elementUtils.getPackageOf(this).toString()
}

fun isSubtypeOfType(typeMirror: TypeMirror, otherType: String): Boolean {
    if (isTypeEqual(typeMirror, otherType)) {
        return true
    }
    if (typeMirror.kind != TypeKind.DECLARED) {
        return false
    }
    val declaredType = typeMirror as DeclaredType
    val typeArguments = declaredType.typeArguments
    if (typeArguments.size > 0) {
        val typeString = StringBuilder(declaredType.asElement().toString())
        typeString.append('<')
        for (i in typeArguments.indices) {
            if (i > 0) {
                typeString.append(',')
            }
            typeString.append('?')
        }
        typeString.append('>')
        if (typeString.toString() == otherType) {
            return true
        }
    }
    val element = declaredType.asElement() as? TypeElement ?: return false
    val superType = element.superclass
    if (isSubtypeOfType(superType, otherType)) {
        return true
    }
    for (interfaceType in element.interfaces) {
        if (isSubtypeOfType(interfaceType, otherType)) {
            return true
        }
    }
    return false
}

fun isTypeEqual(typeMirror: TypeMirror, otherType: String): Boolean {
    return otherType == typeMirror.toString()
}