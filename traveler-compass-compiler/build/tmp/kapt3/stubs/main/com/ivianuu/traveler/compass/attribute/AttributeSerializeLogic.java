package com.ivianuu.traveler.compass.attribute;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 10}, bv = {1, 0, 2}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J(\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH&J0\u0010\f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000eH&\u00a8\u0006\u000f"}, d2 = {"Lcom/ivianuu/traveler/compass/attribute/AttributeSerializeLogic;", "", "addAttributeSerializeLogic", "", "environment", "Ljavax/annotation/processing/ProcessingEnvironment;", "builder", "Lcom/squareup/kotlinpoet/FunSpec$Builder;", "baseElement", "Ljavax/lang/model/element/TypeElement;", "attribute", "Ljavax/lang/model/element/VariableElement;", "addBundleAccessorLogic", "valueName", "", "traveler-compass-compiler"})
public abstract interface AttributeSerializeLogic {
    
    public abstract boolean addAttributeSerializeLogic(@org.jetbrains.annotations.NotNull()
    javax.annotation.processing.ProcessingEnvironment environment, @org.jetbrains.annotations.NotNull()
    com.squareup.kotlinpoet.FunSpec.Builder builder, @org.jetbrains.annotations.NotNull()
    javax.lang.model.element.TypeElement baseElement, @org.jetbrains.annotations.NotNull()
    javax.lang.model.element.VariableElement attribute);
    
    public abstract boolean addBundleAccessorLogic(@org.jetbrains.annotations.NotNull()
    javax.annotation.processing.ProcessingEnvironment environment, @org.jetbrains.annotations.NotNull()
    com.squareup.kotlinpoet.FunSpec.Builder builder, @org.jetbrains.annotations.NotNull()
    javax.lang.model.element.TypeElement baseElement, @org.jetbrains.annotations.NotNull()
    javax.lang.model.element.VariableElement attribute, @org.jetbrains.annotations.NotNull()
    java.lang.String valueName);
}