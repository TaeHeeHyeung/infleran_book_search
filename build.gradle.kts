// Top-level build filvvalvalvalvalale where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugins.ANDROID_APPLICATION) version Versions.AGP apply false
    id(Plugins.ANDROID_LIBRARY) version Versions.AGP apply false
    id(Plugins.KOTLIN_ANDROID) version Versions.KOTLIN apply false
    id(Plugins.DEV_TOOLS_KSP) version Versions.DEV_TOOLS_KSP apply false
    id(Plugins.SECRET_GRADLE_PLUGIN) version Versions.SECRET_GRADLE_PLUGIN apply false
    id(Plugins.SAFE_ARGS) version Versions.SAFE_ARGS apply false
    kotlin(Plugins.KAPT) version Versions.KAPT
    id(Plugins.KOTLIN_SERIALIZATION) version Versions.SERIALIZATION apply false
    id(Plugins.DAGGER_HILT) version Versions.DAGGER_HILT apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}

buildscript {
    dependencies {
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.50")
    }
}