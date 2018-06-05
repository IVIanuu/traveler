package com.ivianuu.traveler.compass.attribute.list;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 10}, bv = {1, 0, 2}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J(\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J0\u0010\u0015\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0004H\u0016R\u0014\u0010\u0003\u001a\u00020\u00048TX\u0094\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00048TX\u0094\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u0012\u0010\t\u001a\u00020\u0004X\u00a4\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u0006\u00a8\u0006\u0017"}, d2 = {"Lcom/ivianuu/traveler/compass/attribute/list/PrimitiveListAttributeSerializeLogic;", "Lcom/ivianuu/traveler/compass/attribute/list/ListAttributeSerializeLogic;", "()V", "boxedPrimitive", "", "getBoxedPrimitive", "()Ljava/lang/String;", "capitalizedPrimitive", "getCapitalizedPrimitive", "primitive", "getPrimitive", "addAttributeSerializeLogic", "", "environment", "Ljavax/annotation/processing/ProcessingEnvironment;", "builder", "Lcom/squareup/kotlinpoet/FunSpec$Builder;", "baseElement", "Ljavax/lang/model/element/TypeElement;", "attribute", "Ljavax/lang/model/element/VariableElement;", "addBundleAccessorLogic", "valueName", "traveler-compass-compiler"})
public abstract class PrimitiveListAttributeSerializeLogic extends com.ivianuu.traveler.compass.attribute.list.ListAttributeSerializeLogic {
    
    @org.jetbrains.annotations.NotNull()
    protected abstract java.lang.String getPrimitive();
    
    @org.jetbrains.annotations.NotNull()
    protected java.lang.String getCapitalizedPrimitive() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    protected java.lang.String getBoxedPrimitive() {
        return null;
    }
    
    @java.lang.Override()
    public boolean addAttributeSerializeLogic(@org.jetbrains.annotations.NotNull()
    javax.annotation.processing.ProcessingEnvironment environment, @org.jetbrains.annotations.NotNull()
    com.squareup.kotlinpoet.FunSpec.Builder builder, @org.jetbrains.annotations.NotNull()
    javax.lang.model.element.TypeElement baseElement, @org.jetbrains.annotations.NotNull()
    javax.lang.model.element.VariableElement attribute) {
        return false;
    }
    
    @java.lang.Override()
    public boolean addBundleAccessorLogic(@org.jetbrains.annotations.NotNull()
    javax.annotation.processing.ProcessingEnvironment environment, @org.jetbrains.annotations.NotNull()
    com.squareup.kotlinpoet.FunSpec.Builder builder, @org.jetbrains.annotations.NotNull()
    javax.lang.model.element.TypeElement baseElement, @org.jetbrains.annotations.NotNull()
    javax.lang.model.element.VariableElement attribute, @org.jetbrains.annotations.NotNull()
    java.lang.String valueName) {
        return false;
    }
    
    public PrimitiveListAttributeSerializeLogic() {
        super();
    }
}