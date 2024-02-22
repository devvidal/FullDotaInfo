plugins {
    id("com.android.application")
    kotlin("android")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    namespace = Android.appId
    compileSdk = Android.compileSdk

    defaultConfig {
        applicationId = Android.appId
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = Android.versionCode
        versionName = Android.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = MyCompose.ComposeKotlinCompiler_Version
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    // Allow references to generated code
    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.heroDomain))
    implementation(project(Modules.heroInteractors))
    implementation(project(Modules.ui_heroList))

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.lifecycleVmKtx)

    // Compose
    val composeBom = platform(MyCompose.composeBom)
    implementation(composeBom)

    implementation(MyCompose.composeRuntime)
    implementation(MyCompose.composeUi)
    implementation(MyCompose.composeMaterial)
    implementation(MyCompose.ComposeActivity)

    implementation(Coil.coil)

    implementation(Google.material)

    implementation(SqlDelight.androidDriver)

    implementation(Hilt.android)
    kapt(Hilt.compiler)
}