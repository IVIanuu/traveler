@file:Suppress("ClassName", "unused")

object Versions {
    // android
    const val compileSdk = 28
    const val minSdk = 16
    const val targetSdk = 28
    const val versionCode = 1
    const val versionName = "1.0"

    const val androidGradlePlugin = "3.1.4"

    const val archLifecycle = "1.1.1"

    const val kotlin = "1.2.61"
    const val mavenGradlePlugin = "2.1"
    const val support = "28.0.0-alpha3"
}

object Deps {
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"

    const val archLifecycleExtensions = "android.arch.lifecycle:extensions:${Versions.archLifecycle}"
    const val archLifecycleCompiler = "android.arch.lifecycle:compiler:${Versions.archLifecycle}"

    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jre7:${Versions.kotlin}"

    const val mavenGradlePlugin = "com.github.dcendents:android-maven-gradle-plugin:${Versions.mavenGradlePlugin}"

    const val supportAppCompat = "com.android.support:appcompat-v7:${Versions.support}"
    const val supportFragments = "com.android.support:support-fragment:${Versions.support}"
}