plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp") version "2.2.0-2.0.2"
}

android {
    namespace = "com.albertocamillo.onthisdaymvi"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.albertocamillo.onthisdaymvi"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Core Kotlin and Android extensions
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)

    // Lifecycle and ViewModel
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Jetpack Compose BOM and UI
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Networking with Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Room for local database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.ui.test.junit4.android)
    ksp(libs.androidx.room.compiler)

    // Unit Testing
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk) // Or use Mockito
    testImplementation(libs.turbine) // Optional for Flow testing

    // Android Instrumented (UI) Testing
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.ui.test.junit4)

    // Debug utilities for UI testing
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    debugImplementation(libs.ui.test.manifest)
}
