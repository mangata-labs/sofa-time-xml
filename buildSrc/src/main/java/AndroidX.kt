object AndroidX {
    private const val appCompactVersion = "1.5.1"
    const val appCompact = "androidx.appcompat:appcompat:$appCompactVersion"

    private const val constraintLayoutVersion = "2.1.4"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"

    private const val coreKtxVersion = "1.9.0"
    const val coreKtx = "androidx.core:core-ktx:$coreKtxVersion"

    private const val lifecycleVmKtxVersion = "2.5.1"
    const val lifecycleKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVmKtxVersion"

    const val navigationVersion = "2.5.3"
    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:$navigationVersion"
    const val navigationKtx = "androidx.navigation:navigation-ui-ktx:$navigationVersion"
}

object AndroidXTest {
    private const val testCoreVersion = "1.5.0"
    const val core = "androidx.test:core-ktx:$testCoreVersion"

    private const val junitExtVersion = "1.1.4"
    const val junitExt = "androidx.test.ext:junit-ktx:$junitExtVersion"
}