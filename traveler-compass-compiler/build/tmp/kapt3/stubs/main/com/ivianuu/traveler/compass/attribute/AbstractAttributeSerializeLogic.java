package com.ivianuu.traveler.compass.attribute;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 10}, bv = {1, 0, 2}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\b\b&\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0004J(\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0006H\u0004JH\u0010\u0010\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u00042\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0004H\u0004J0\u0010\u0016\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0004H\u0004J\u0018\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0006H\u0002\u00a8\u0006\u0019"}, d2 = {"Lcom/ivianuu/traveler/compass/attribute/AbstractAttributeSerializeLogic;", "Lcom/ivianuu/traveler/compass/attribute/AttributeSerializeLogic;", "()V", "bundleArgumentName", "", "element", "Ljavax/lang/model/element/VariableElement;", "destination", "Ljavax/lang/model/element/TypeElement;", "createAccessor", "environment", "Ljavax/annotation/processing/ProcessingEnvironment;", "builder", "Lcom/squareup/kotlinpoet/FunSpec$Builder;", "baseElement", "attribute", "createBundleGet", "", "getMethodName", "valueName", "castTo", "parameter", "createBundlePut", "putMethodName", "throwNoAccessorFound", "traveler-compass-compiler"})
public abstract class AbstractAttributeSerializeLogic implements com.ivianuu.traveler.compass.attribute.AttributeSerializeLogic {
    
    protected final void createBundlePut(@org.jetbrains.annotations.NotNull()
    com.squareup.kotlinpoet.FunSpec.Builder builder, @org.jetbrains.annotations.NotNull()
    javax.lang.model.element.VariableElement attribute, @org.jetbrains.annotations.NotNull()
    javax.lang.model.element.TypeElement baseElement, @org.jetbrains.annotations.NotNull()
    java.lang.String putMethodName, @org.jetbrains.annotations.NotNull()
    java.lang.String valueName) {
    }
    
    protected final void createBundleGet(@org.jetbrains.annotations.NotNull()
    com.squareup.kotlinpoet.FunSpec.Builder builder, @org.jetbrains.annotations.NotNull()
    javax.lang.model.element.VariableElement attribute, @org.jetbrains.annotations.NotNull()
    javax.lang.model.element.TypeElement baseElement, @org.jetbrains.annotations.NotNull()
    java.lang.String getMethodName, @org.jetbrains.annotations.NotNull()
    java.lang.String valueName, @org.jetbrains.annotations.Nullable()
    java.lang.String castTo, @org.jetbrains.annotations.Nullable()
    java.lang.String parameter) {
    }
    
    @org.jetbrains.annotations.NotNull()
    protected final java.lang.String createAccessor(@org.jetbrains.annotations.NotNull()
    javax.annotation.processing.ProcessingEnvironment environment, @org.jetbrains.annotations.NotNull()
    com.squareup.kotlinpoet.FunSpec.Builder builder, @org.jetbrains.annotations.NotNull()
    javax.lang.model.element.TypeElement baseElement, @org.jetbrains.annotations.NotNull()
    javax.lang.model.element.VariableElement attribute) {
        return null;
    }
    
    private final void throwNoAccessorFound(javax.lang.model.element.TypeElement baseElement, javax.lang.model.element.VariableElement attribute) {
    }
    
    @org.jetbrains.annotations.NotNull()
    protected final java.lang.String bundleArgumentName(@org.jetbrains.annotations.NotNull()
    javax.lang.model.element.VariableElement element, @org.jetbrains.annotations.NotNull()
    javax.lang.model.element.TypeElement destination) {
        return null;
    }
    
    public AbstractAttributeSerializeLogic() {
        super();
    }
}