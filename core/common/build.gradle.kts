plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    id("kotlin-kapt")
}

android {
    namespace = "space.song.common"
    compileSdk = 35

    defaultConfig {
        minSdk = 30

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // 通用依赖
    implementation(libs.android.core)
    implementation(libs.compose.ui)
    implementation(libs.compose.material)
    // 所有Android库模块都需要这两个依赖
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    // 如果该模块需要提供Hilt绑定
    kapt(libs.androidx.hilt.compiler)
    //kapt(libs.hilt.compiler)
}
