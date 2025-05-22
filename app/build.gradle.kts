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

        testInstrumentationRunner = "com.dvidal.fulldotainfo.CustomTestRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeKotlinCompilerVersion
    }

    kotlinOptions {
        jvmTarget = "18"
    }

    // Allow references to generated code
    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.constants))
    implementation(project(Modules.heroDataSource))
    implementation(project(Modules.heroDomain))
    implementation(project(Modules.heroInteractors))
    implementation(project(Modules.ui_heroList))
    implementation(project(Modules.ui_heroDetail))

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.lifecycleVmKtx)

    // Compose
    val composeBom = platform(Compose.composeBom)
    implementation(composeBom)

    implementation(Compose.composeRuntime)
    implementation(Compose.composeUi)
    implementation(Compose.composeMaterial)
    implementation(Compose.composeActivity)
    implementation(Compose.composeActivity)
    implementation(Compose.composeNavigation)

    implementation(Coil.coil)

    implementation(Google.material)

    implementation(SqlDelight.androidDriver)

    implementation(Hilt.android)
    implementation(Hilt.navigationCompose)
    kapt(Hilt.compiler)

    androidTestImplementation(project(Modules.heroDataSourceTest))
    androidTestImplementation(AndroidXTest.runner)
    androidTestImplementation(ComposeTest.uiTestJunit4)
    androidTestImplementation(HiltTest.hiltAndroidTesting)
    kaptAndroidTest(Hilt.compiler)
    androidTestImplementation(Junit.junit4)
}