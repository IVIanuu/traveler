package com.ivianuu.traveler.compass.attribute.list;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 10}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0004J\u001c\u0010\t\u001a\u00020\n*\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0004\u00a8\u0006\f"}, d2 = {"Lcom/ivianuu/traveler/compass/attribute/list/ListAttributeSerializeLogic;", "Lcom/ivianuu/traveler/compass/attribute/AbstractAttributeSerializeLogic;", "()V", "getListType", "Ljavax/lang/model/type/DeclaredType;", "environment", "Ljavax/annotation/processing/ProcessingEnvironment;", "elementType", "Ljavax/lang/model/type/TypeMirror;", "isListOfType", "", "Ljavax/lang/model/element/VariableElement;", "traveler-compass-compiler"})
public abstract class ListAttributeSerializeLogic extends com.ivianuu.traveler.compass.attribute.AbstractAttributeSerializeLogic {
    
    @org.jetbrains.annotations.NotNull()
    protected final javax.lang.model.type.DeclaredType getListType(@org.jetbrains.annotations.NotNull()
    javax.annotation.processing.ProcessingEnvironment environment, @org.jetbrains.annotations.NotNull()
    javax.lang.model.type.TypeMirror elementType) {
        return null;
    }
    
    protected final boolean isListOfType(@org.jetbrains.annotations.NotNull()
    javax.lang.model.element.VariableElement $receiver, @org.jetbrains.annotations.NotNull()
    javax.annotation.processing.ProcessingEnvironment environment, @org.jetbrains.annotations.NotNull()
    javax.lang.model.type.TypeMirror elementType) {
        return false;
    }
    
    public ListAttributeSerializeLogic() {
        super();
    }
}