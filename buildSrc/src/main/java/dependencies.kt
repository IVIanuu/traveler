@file:Suppress("ClassName", "unused")

object Build {
    const val applicationId = "com.ivianuu.traveler.sample"
    const val buildToolsVersion = "28.0.3"
    const val compileSdk = 28
    const val minSdk = 16
    const val targetSdk = 28

    const val versionCode = 1
    const val versionName = "0.0.1"
}

object Versions {
    const val androidGradlePlugin = "3.2.1"

    const val androidx = "1.0.0"
    const val androidxArch = "2.0.0-rc01"
    const val androidxTestRules = "1.1.0"
    const val androidxTestRunner = "1.1.0"

    const val junit = "4.12"

    const val kotlin = "1.3.0"
    const val mavenGradlePlugin = "2.1"
    const val materialComponents = "1.0.0"

    const val roboelectric = "4.0.2"
}

object Deps {
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"

    const val androidxAppCompat = "androidx.appcompat:appcompat:${Versions.androidx}"
    const val androidxFragment = "androidx.fragment:fragment:${Versions.androidx}"
    const val androidxTestCore = "androidx.test:core:${Versions.androidx}"
    const val androidxTestJunit = "androidx.test.ext:junit:${Versions.androidx}"
    const val androidxTestRules = "androidx.test:rules:${Versions.androidxTestRules}"
    const val androidxTestRunner = "androidx.test:runner:${Versions.androidxTestRunner}"

    const val archLifecycleExtensions =
        "androidx.lifecycle:lifecycle-extensions:${Versions.androidxArch}"

    const val junit = "junit:junit:${Versions.junit}"

    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    const val mavenGradlePlugin = "com.github.dcendents:android-maven-gradle-plugin:${Versions.mavenGradlePlugin}"

    const val materialComponents =
        "com.google.android.material:material:${Versions.materialComponents}"

    const val roboelectric = "org.robolectric:robolectric:${Versions.roboelectric}"
}