apply(from = "$rootDir/android-library-build.gradle")
apply(plugin = "androidx.navigation.safeargs")

dependencies {
    "implementation" (project(Modules.tvShowDomain))

    "implementation" (AndroidX.navigationFragment)
    "implementation" (AndroidX.navigationKtx)

    "implementation" (Koin.koinAndroid)
    "implementation" (Coil.coilAndroid)
}