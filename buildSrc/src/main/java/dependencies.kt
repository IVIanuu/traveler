@file:Suppress("ClassName", "unused")

object Versions {
    // android
    const val compileSdk = 27
    const val minSdk = 16
    const val targetSdk = 27
    const val versionCode = 1
    const val versionName = "1.0"

    const val androidGradlePlugin = "3.1.2"

    const val autoCommon = "0.8"
    const val autoService = "1.0-rc3"

    const val conductor = "2.1.4"

    const val kotlinPoet = "0.7.0"

    const val kotlin = "1.2.41"
    const val mavenGradlePlugin = "2.1"
    const val support = "27.1.1"
}
//
object Deps {
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"

    const val autoCommon = "com.google.auto:auto-common:${Versions.autoCommon}"

    const val autoService = "com.google.auto.service:auto-service:${Versions.autoService}"

    const val conductor = "com.bluelinelabs:conductor:${Versions.conductor}"

    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jre7:${Versions.kotlin}"

    const val kotlinPoet = "com.squareup:kotlinpoet:${Versions.kotlinPoet}"

    const val mavenGradlePlugin = "com.github.dcendents:android-maven-gradle-plugin:${Versions.mavenGradlePlugin}"

    const val supportAppCompat = "com.android.support:appcompat-v7:${Versions.support}"
    const val supportFragments = "com.android.support:support-fragment:${Versions.support}"
}