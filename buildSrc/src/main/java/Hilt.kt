object Hilt {
    const val hiltVersion = "2.50"
    const val android = "com.google.dagger:hilt-android:$hiltVersion"
    const val compiler = "com.google.dagger:hilt-compiler:$hiltVersion"

    private const val navigationComposeVersion = "1.0.0"
    const val navigationComposeFragment = "androidx.hilt:hilt-navigation-fragment:$navigationComposeVersion"
    const val navigationCompose = "androidx.hilt:hilt-navigation-compose:$navigationComposeVersion"
}

object HiltTest {
    const val hiltAndroidTesting = "com.google.dagger:hilt-android-testing:${Hilt.hiltVersion}"
}