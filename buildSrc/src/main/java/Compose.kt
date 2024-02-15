object Compose {
    private const val activityComposeVersion = "1.8.2"
    const val activity = "androidx.activity:activity-compose:$activityComposeVersion"

    const val composeKotlinVersion = "1.4.3"
    const val composeVersion = "1.3.3"
    const val composeMaterialVersion = "1.3.1"
    const val ui = "androidx.compose.ui:ui:$composeVersion"
    const val material = "androidx.compose.material:material:$composeMaterialVersion"
    const val tooling = "androidx.compose.ui:ui-tooling:$composeVersion"

    private const val navigationVersion = "2.5.3"
    const val navigation = "androidx.navigation:navigation-compose:$navigationVersion"

    private const val hiltNavigationComposeVersion = "1.0.0"
    const val hiltNavigation = "androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion"
}

object MyCompose {

    private const val activityComposeVersion = "1.8.2"
    const val ComposeKotlinCompiler_Version = "1.5.9"
    const val ComposeBom_Version = "2024.02.00"
    const val ComposeBom = "androidx.compose:compose-bom:$ComposeBom_Version"
    const val ComposeActivity = "androidx.activity:activity-compose:$activityComposeVersion"
    const val ComposeRuntime = "androidx.compose.runtime:runtime"
    const val ComposeLifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-compose"
    const val ComposeUI = "androidx.compose.ui:ui"
    const val ComposeMaterial = "androidx.compose.material:material"
    const val ComposeTooling = "androidx.compose.ui:ui-tooling"
}

object ComposeTest {
    const val uiTestJunit4 = "androidx.compose.ui:ui-test-junit4:${Compose.composeVersion}"
    const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:${Compose.composeVersion}"
}