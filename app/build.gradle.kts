plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

        buildConfigField("String", ApiKeys.TMDB_API_KEY, ApiKeys.getTmdbApiKey)
        buildConfigField("String", ApiKeys.TRAKT_CLIENT_ID, ApiKeys.getTraktClientID)

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

    buildFeatures {
        viewBinding = true
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
    implementation(project(Modules.tvShowDomain))
    implementation(project(Modules.tvShowData))
    implementation(project(Modules.coreUI))

    implementation(AndroidX.appCompact)
    implementation(AndroidX.constraintLayout)
    implementation(Google.material)
    implementation(AndroidX.coreKtx)
    implementation(AndroidX.lifecycleKtx)

    implementation(AndroidX.navigationFragment)
    implementation(AndroidX.navigationKtx)

    implementation(Koin.koinAndroid)
    implementation(Koin.koinNavigation)

    implementation(Coil.coilAndroid)
}
