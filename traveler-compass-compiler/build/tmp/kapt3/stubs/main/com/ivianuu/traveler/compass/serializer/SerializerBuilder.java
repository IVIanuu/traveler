package com.ivianuu.traveler.compass.serializer;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 10}, bv = {1, 0, 2}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011J\u001e\u0010\u0012\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/ivianuu/traveler/compass/serializer/SerializerBuilder;", "", "()V", "METHOD_NAME_FROM_BUNDLE", "", "METHOD_NAME_TO_BUNDLE", "PARAM_BUNDLE", "PARAM_DESTINATION", "logicParts", "", "Lcom/ivianuu/traveler/compass/attribute/AttributeSerializeLogic;", "addFromBundleMethod", "Lcom/squareup/kotlinpoet/TypeSpec$Builder;", "environment", "Ljavax/annotation/processing/ProcessingEnvironment;", "builder", "element", "Ljavax/lang/model/element/TypeElement;", "addToBundleMethod", "traveler-compass-compiler"})
public final class SerializerBuilder {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PARAM_BUNDLE = "bundle";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PARAM_DESTINATION = "destination";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String METHOD_NAME_FROM_BUNDLE = "readFromBundle";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String METHOD_NAME_TO_BUNDLE = "writeToBundle";
    private static final java.util.List<com.ivianuu.traveler.compass.attribute.AttributeSerializeLogic> logicParts = null;
    public static final com.ivianuu.traveler.compass.serializer.SerializerBuilder INSTANCE = null;
    
    @org.jetbrains.annotations.NotNull()
    public final com.squareup.kotlinpoet.TypeSpec.Builder addToBundleMethod(@org.jetbrains.annotations.NotNull()
    javax.annotation.processing.ProcessingEnvironment environment, @org.jetbrains.annotations.NotNull()
    com.squareup.kotlinpoet.TypeSpec.Builder builder, @org.jetbrains.annotations.NotNull()
    javax.lang.model.element.TypeElement element) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.squareup.kotlinpoet.TypeSpec.Builder addFromBundleMethod(@org.jetbrains.annotations.NotNull()
    javax.annotation.processing.ProcessingEnvironment environment, @org.jetbrains.annotations.NotNull()
    com.squareup.kotlinpoet.TypeSpec.Builder builder, @org.jetbrains.annotations.NotNull()
    javax.lang.model.element.TypeElement element) {
        return null;
    }
    
    private SerializerBuilder() {
        super();
    }
}