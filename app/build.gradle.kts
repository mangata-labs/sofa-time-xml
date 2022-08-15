plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs")
}

android {
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(Modules.core))
    implementation(Modules.tvShowDomain)
    implementation(Modules.tvShowData)

    implementation(AndroidX.appCompact)
    implementation(AndroidX.constraintLayout)
    implementation(Google.material)
    implementation(AndroidX.coreKtx)
    implementation(AndroidX.lifecycleKtx)

    implementation(AndroidX.navigationFragment)
    implementation(AndroidX.navigationKtx)

    implementation(Koin.koinAndroid)
    implementation(Koin.koinNavigation)
}
