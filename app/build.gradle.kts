plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.german.flip"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.german.flip"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Compose BOM (Bill of Materials) to manage versions
    implementation(platform(libs.androidx.compose.bom)) // Adjust to the latest BOM version

    // Core Compose libraries
    implementation(libs.androidx.ui)
    implementation(libs.material3)
    implementation(libs.ui.tooling.preview)

    // Optional dependencies
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    // For AndroidX Activity support
    implementation(libs.activity.compose)

    // For ViewModel support
    implementation(libs.lifecycle.viewmodel.compose)
}