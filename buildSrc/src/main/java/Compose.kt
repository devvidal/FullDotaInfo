object Compose {

    const val composeVersion = "1.3.3"

    private const val activityComposeVersion = "1.8.2"
    const val composeKotlinCompilerVersion = "1.5.9"
    private const val composeBomVersion = "2024.02.00"

    const val composeBom = "androidx.compose:compose-bom:$composeBomVersion"
    const val composeActivity = "androidx.activity:activity-compose:$activityComposeVersion"
    const val composeRuntime = "androidx.compose.runtime:runtime"
    const val composeUi = "androidx.compose.ui:ui"
    const val composeMaterial = "androidx.compose.material:material"
    const val composeTooling = "androidx.compose.ui:ui-tooling"

    private const val composeNavigationVersion = "2.7.7"
    const val composeNavigation = "androidx.navigation:navigation-compose:$composeNavigationVersion"
}

object ComposeTest {
    const val uiTestJunit4 = "androidx.compose.ui:ui-test-junit4:${Compose.composeVersion}"
    const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:${Compose.composeVersion}"
}