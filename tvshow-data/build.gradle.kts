apply(from = "$rootDir/android-library-build.gradle")
apply(plugin = "org.jetbrains.kotlin.plugin.serialization")
apply(plugin = "kotlin-kapt")

dependencies {
    "implementation" (project(Modules.core))
    "implementation" (project(Modules.tvShowDomain))

    "implementation" (KotlinX.coroutinesCore)
    "implementation" (KotlinX.serialization)

    "implementation" (Ktor.ktorCore)
    "implementation" (Ktor.ktorAndroid)
    "implementation" (Ktor.ktorContentNegotiation)
    "implementation" (Ktor.ktorSerialization)
    "implementation" (Ktor.ktorLogging)

    "implementation" (Room.roomRuntime)
    "kapt" (Room.roomCompiler)
    "implementation" (Room.roomKtx)

    "implementation" (Koin.koinAndroid)
}