plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.wearablelist"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.wearablelist"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
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
    // Wear OS core dependencies
    implementation(libs.play.services.wearable)
    implementation(libs.core.splashscreen)

    // Compose BOM - Ensures compatible versions
    implementation(platform(libs.compose.bom))

    // Compose UI
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.activity.compose)

    // Wear Compose - Material Design for Wear OS
    implementation(libs.compose.material)
    implementation(libs.compose.foundation)

    // Navigation Compose
    implementation(libs.navigation.compose)

    // Wear specific tooling
    implementation(libs.wear.tooling.preview)

    // Testing dependencies
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)

    // Debug dependencies
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}