package com.ivianuu.traveler.compass;

import java.lang.System;

@com.google.auto.service.AutoService(value = javax.annotation.processing.Processor.class)
@kotlin.Metadata(mv = {1, 1, 10}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010#\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0002J\u0016\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0002J\u0016\u0010\n\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0002J\u0010\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\tH\u0002J\u0016\u0010\r\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0002J\u0016\u0010\u000e\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0002J\u0010\u0010\u000f\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\tH\u0002J\u0016\u0010\u0010\u001a\u0010\u0012\f\u0012\n \u0012*\u0004\u0018\u00010\u00040\u00040\u0011H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J \u0010\u0018\u001a\u00020\u00192\u000e\u0010\u001a\u001a\n\u0012\u0006\b\u0001\u0012\u00020\t0\u00112\u0006\u0010\u0016\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u0016\u001a\u00020\u001bH\u0002J\u0010\u0010\u001d\u001a\u00020\u00192\u0006\u0010\u0016\u001a\u00020\u001bH\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/ivianuu/traveler/compass/CompassProcessor;", "Ljavax/annotation/processing/AbstractProcessor;", "()V", "extPackage", "", "generateCrane", "", "elements", "", "Ljavax/lang/model/element/TypeElement;", "generateDetourPilot", "generateExtensions", "base", "generateMap", "generateNavigator", "generateSerializer", "getSupportedAnnotationTypes", "", "kotlin.jvm.PlatformType", "getSupportedSourceVersion", "Ljavax/lang/model/SourceVersion;", "init", "env", "Ljavax/annotation/processing/ProcessingEnvironment;", "process", "", "set", "Ljavax/annotation/processing/RoundEnvironment;", "processDestinations", "processDetours", "Companion", "traveler-compass-compiler"})
public final class CompassProcessor extends javax.annotation.processing.AbstractProcessor {
    private java.lang.String extPackage;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DEFAULT_EXT_PACKAGE = "com.ivianuu.traveler.compass";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String OPTION_EXT_PACKAGE = "ext_package";
    public static final com.ivianuu.traveler.compass.CompassProcessor.Companion Companion = null;
    
    @java.lang.Override()
    public void init(@org.jetbrains.annotations.NotNull()
    javax.annotation.processing.ProcessingEnvironment env) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.util.Set<java.lang.String> getSupportedAnnotationTypes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public javax.lang.model.SourceVersion getSupportedSourceVersion() {
        return null;
    }
    
    @java.lang.Override()
    public boolean process(@org.jetbrains.annotations.NotNull()
    java.util.Set<? extends javax.lang.model.element.TypeElement> set, @org.jetbrains.annotations.NotNull()
    javax.annotation.processing.RoundEnvironment env) {
        return false;
    }
    
    private final boolean processDestinations(javax.annotation.processing.RoundEnvironment env) {
        return false;
    }
    
    private final boolean processDetours(javax.annotation.processing.RoundEnvironment env) {
        return false;
    }
    
    private final void generateSerializer(javax.lang.model.element.TypeElement base) {
    }
    
    private final void generateExtensions(javax.lang.model.element.TypeElement base) {
    }
    
    private final void generateCrane(java.util.List<? extends javax.lang.model.element.TypeElement> elements) {
    }
    
    private final void generateMap(java.util.List<? extends javax.lang.model.element.TypeElement> elements) {
    }
    
    private final void generateNavigator(java.util.List<? extends javax.lang.model.element.TypeElement> elements) {
    }
    
    private final void generateDetourPilot(java.util.List<? extends javax.lang.model.element.TypeElement> elements) {
    }
    
    private final java.lang.String extPackage() {
        return null;
    }
    
    public CompassProcessor() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 10}, bv = {1, 0, 2}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/ivianuu/traveler/compass/CompassProcessor$Companion;", "", "()V", "DEFAULT_EXT_PACKAGE", "", "OPTION_EXT_PACKAGE", "traveler-compass-compiler"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}