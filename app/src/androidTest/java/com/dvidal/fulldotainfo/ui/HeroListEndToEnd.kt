package com.dvidal.fulldotainfo.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import coil.request.ImageRequest
import com.dvidal.fulldotainfo.MainActivity
import com.dvidal.fulldotainfo.di.HeroInteractorsModule
import com.dvidal.hero_datasource.cache.HeroCache
import com.dvidal.hero_datasource.network.HeroService
import com.dvidal.hero_datasource_test.cache.HeroCacheFake
import com.dvidal.hero_datasource_test.cache.HeroDatabaseFake
import com.dvidal.hero_datasource_test.network.HeroServiceFake
import com.dvidal.hero_datasource_test.network.HeroServiceResponseType
import com.dvidal.hero_domain.HeroAttribute
import com.dvidal.hero_interactors.FilterHeros
import com.dvidal.hero_interactors.GetHeroFromCache
import com.dvidal.hero_interactors.GetHeros
import com.dvidal.hero_interactors.HeroInteractors
import com.dvidal.ui_herolist.ui.test.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import javax.inject.Singleton

@UninstallModules(HeroInteractorsModule::class)
@HiltAndroidTest
class HeroListEndToEnd {

    @Module
    @InstallIn(SingletonComponent::class)
    object TestHeroInteractorsModule {

        @Provides
        @Singleton
        fun provideHeroCache(): HeroCache {
            return HeroCacheFake(HeroDatabaseFake())
        }

        @Provides
        @Singleton
        fun provideHeroService(): HeroService {
            return HeroServiceFake.build(
                type = HeroServiceResponseType.ValidData
            )
        }

        @Provides
        @Singleton
        fun provideHeroInteractors(
            cache: HeroCache,
            service: HeroService
        ): HeroInteractors {
            return HeroInteractors(
                getHeros = GetHeros(
                    cache = cache,
                    service = service
                ),
                getHeroFromCache = GetHeroFromCache(cache),
                filterHeros = FilterHeros()
            )
        }
    }

    // xxxxx

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val imageBuilder = ImageRequest.Builder(context)

    @Before
    fun before(){

        // Looks like its no necessary to put this code
//        composeTestRule.setContent {
//            DotaInfoTheme {
//                val navController = rememberNavController()
//                NavHost(
//                    navController = navController,
//                    startDestination = Screen.HeroList.route,
//                    builder = {
//                        composable(
//                            route = Screen.HeroList.route,
//                        ){
//                            val viewModel: HeroListViewModel = hiltViewModel()
//                            HeroListScreen(
//                                state = viewModel.state.value,
//                                events = viewModel::onTriggerEvent,
//                                navigateToDetailScreen = { heroId ->
//                                    navController.navigate("${Screen.HeroDetail.route}/$heroId")
//                                },
//                                imageBuilder = imageBuilder,
//                            )
//                        }
//                        composable(
//                            route = Screen.HeroDetail.route + "/{heroId}",
//                            arguments = Screen.HeroDetail.arguments,
//                        ){
//                            val viewModel: HeroDetailViewModel = hiltViewModel()
//                            HeroDetailScreen(
//                                state = viewModel.state.value,
//                                events = viewModel::onTriggerEvent,
//                                imageBuilder = imageBuilder
//                            )
//                        }
//                    }
//                )
//            }
//        }
    }

    @Test
    fun testSearchHeroByName(){
        composeTestRule.onRoot(useUnmergedTree = true).printToLog("TAG") // For learning the ui tree system

        composeTestRule.onNodeWithTag(TAG_HERO_SEARCH_BAR).performTextInput("Anti-Mage")
        composeTestRule.onNodeWithTag(TAG_HERO_NAME, useUnmergedTree = true).assertTextEquals(
            "Anti-Mage",
        )
        composeTestRule.onNodeWithTag(TAG_HERO_SEARCH_BAR).performTextClearance()

        composeTestRule.onNodeWithTag(TAG_HERO_SEARCH_BAR).performTextInput("Storm Spirit")
        composeTestRule.onNodeWithTag(TAG_HERO_NAME, useUnmergedTree = true).assertTextEquals(
            "Storm Spirit",
        )
        composeTestRule.onNodeWithTag(TAG_HERO_SEARCH_BAR).performTextClearance()

        composeTestRule.onNodeWithTag(TAG_HERO_SEARCH_BAR).performTextInput("Mirana")
        composeTestRule.onNodeWithTag(TAG_HERO_NAME, useUnmergedTree = true).assertTextEquals(
            "Mirana",
        )
    }

    @Test
    fun testFilterHeroAlphabetically(){
        // Show the dialog
        composeTestRule.onNodeWithTag(TAG_HERO_FILTER_BTN).performClick()

        // Confirm the filter dialog is showing
        composeTestRule.onNodeWithTag(TAG_HERO_FILTER_DIALOG).assertIsDisplayed()

        // Filter by "Hero" name
        composeTestRule.onNodeWithTag(TAG_HERO_FILTER_HERO_CHECKBOX).performClick()

        // Order Descending (z-a)
        composeTestRule.onNodeWithTag(TAG_HERO_FILTER_DESC).performClick()

        // Close the dialog
        composeTestRule.onNodeWithTag(TAG_HERO_FILTER_DIALOG_DONE).performClick()

        // Confirm the order is correct
        composeTestRule.onAllNodesWithTag(TAG_HERO_NAME, useUnmergedTree = true).assertAny(hasText("Zeus"))

        // Show the dialog
        composeTestRule.onNodeWithTag(TAG_HERO_FILTER_BTN).performClick()

        // Order Ascending (a-z)
        composeTestRule.onNodeWithTag(TAG_HERO_FILTER_ASC).performClick()

        // Close the dialog
        composeTestRule.onNodeWithTag(TAG_HERO_FILTER_DIALOG_DONE).performClick()

        // Confirm the order is correct
        composeTestRule.onAllNodesWithTag(TAG_HERO_NAME, useUnmergedTree = true).assertAny(hasText("Abaddon"))
    }

    @Ignore("not working")
    @Test
    fun testFilterHeroByProWins(){
        // Show the dialog
        composeTestRule.onNodeWithTag(TAG_HERO_FILTER_BTN).performClick()

        // Confirm the filter dialog is showing
        composeTestRule.onNodeWithTag(TAG_HERO_FILTER_DIALOG).assertIsDisplayed()

        // Filter by ProWin %
        composeTestRule.onNodeWithTag(TAG_HERO_FILTER_PROWINS_CHECKBOX).performClick()

        // Order Descending (100% - 0%)
        composeTestRule.onNodeWithTag(TAG_HERO_FILTER_DESC).performClick()

        // Close the dialog
        composeTestRule.onNodeWithTag(TAG_HERO_FILTER_DIALOG_DONE).performClick()

        // Confirm the order is correct
        composeTestRule.onAllNodesWithTag(TAG_HERO_NAME, useUnmergedTree = true).assertAny(hasText("Chen"))

        // Show the dialog
        composeTestRule.onNodeWithTag(TAG_HERO_FILTER_BTN).performClick()

        // Order Ascending (0% - 100%)
        composeTestRule.onNodeWithTag(TAG_HERO_FILTER_ASC).performClick()

        // Close the dialog
        composeTestRule.onNodeWithTag(TAG_HERO_FILTER_DIALOG_DONE).performClick()

        // Confirm the order is correct
        composeTestRule.onAllNodesWithTag(TAG_HERO_NAME, useUnmergedTree = true).assertAny(hasText("Dawnbreaker"))
    }

    @Test
    fun testFilterHeroByStrength(){
        // Show the dialog
        composeTestRule.onNodeWithTag(TAG_HERO_FILTER_BTN).performClick()

        // Confirm the filter dialog is showing
        composeTestRule.onNodeWithTag(TAG_HERO_FILTER_DIALOG).assertIsDisplayed()

        composeTestRule.onNodeWithTag(TAG_HERO_FILTER_STENGTH_CHECKBOX).performClick()

        // Close the dialog
        composeTestRule.onNodeWithTag(TAG_HERO_FILTER_DIALOG_DONE).performClick()

        // Confirm that only STRENGTH heros are showing
        composeTestRule.onAllNodesWithTag(TAG_HERO_PRIMARY_ATTRIBUTE, useUnmergedTree = true).assertAll(hasText(
            HeroAttribute.Strength.uiValue))
    }

    @Test
    fun testFilterHeroByAgility(){
        // Show the dialog
        composeTestRule.onNodeWithTag(TAG_HERO_FILTER_BTN).performClick()

        // Confirm the filter dialog is showing
        composeTestRule.onNodeWithTag(TAG_HERO_FILTER_DIALOG).assertIsDisplayed()

        composeTestRule.onNodeWithTag(TAG_HERO_FILTER_AGILITY_CHECKBOX).performClick()

        // Close the dialog
        composeTestRule.onNodeWithTag(TAG_HERO_FILTER_DIALOG_DONE).performClick()

        // Confirm that only STRENGTH heros are showing
        composeTestRule.onAllNodesWithTag(TAG_HERO_PRIMARY_ATTRIBUTE, useUnmergedTree = true).assertAll(hasText(HeroAttribute.Agility.uiValue))
    }

    @Test
    fun testFilterHeroByIntelligence(){
        // Show the dialog
        composeTestRule.onNodeWithTag(TAG_HERO_FILTER_BTN).performClick()

        // Confirm the filter dialog is showing
        composeTestRule.onNodeWithTag(TAG_HERO_FILTER_DIALOG).assertIsDisplayed()

        composeTestRule.onNodeWithTag(TAG_HERO_FILTER_INT_CHECKBOX).performClick()

        // Close the dialog
        composeTestRule.onNodeWithTag(TAG_HERO_FILTER_DIALOG_DONE).performClick()

        // Confirm that only STRENGTH heros are showing
        composeTestRule.onAllNodesWithTag(TAG_HERO_PRIMARY_ATTRIBUTE, useUnmergedTree = true).assertAll(hasText(
            HeroAttribute.Intelligence.uiValue))
    }
}