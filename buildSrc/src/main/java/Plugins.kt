object Plugins {
    // 앱 빌드에 필요한 플러그인
    const val ANDROID_APPLICATION = "com.android.application"
    // 안드로이드 라이브러리 플러그인
    const val ANDROID_LIBRARY = "com.android.library"
    // 코틀린 플러그인
    const val KOTLIN_ANDROID = "org.jetbrains.kotlin.android"
    const val KOTLIN_PARCELIZE = "kotlin-parcelize"
    const val KOTLIN_KAPT = "kotlin-kapt"

    // 코틀린 어노테이션 프로세싱
    const val DEV_TOOLS_KSP = "com.google.devtools.ksp"
    const val SECRET_GRADLE_PLUGIN = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin"
    const val SAFE_ARGS = "androidx.navigation.safeargs.kotlin"
    const val KOTLIN_SERIALIZATION = "org.jetbrains.kotlin.plugin.serialization"

    // 데거 힐트 의존성 설정
    const val DAGGER_HILT = "com.google.dagger.hilt.android"
    // 데거 힐트 코드 생성,빌드 작업
    const val HILT_PLUGIN = "dagger.hilt.android.plugin"
    // 데거 Anottation 동작 활성화
    const val KAPT = "kapt"
}