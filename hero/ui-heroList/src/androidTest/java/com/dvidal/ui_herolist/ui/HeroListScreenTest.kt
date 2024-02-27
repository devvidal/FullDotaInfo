package com.dvidal.ui_herolist.ui

import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import coil.request.ImageRequest
import com.dvidal.hero_datasource_test.network.data.HeroDataValid
import com.dvidal.hero_datasource_test.network.serializeHeroData
import org.junit.Rule
import org.junit.Test

class HeroListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val heroData = serializeHeroData(HeroDataValid.data)
    private val imageBuilder = ImageRequest.Builder(context)

    @Test
    fun areHerosShown() {

        composeTestRule.setContent {
            val state = remember {
                HeroListState(
                    heros = heroData,
                    filteredHeros = heroData
                )
            }

            HeroListScreen(
                state = state,
                events = {},
                imageBuilder = imageBuilder,
                navigateToDetailScreen = {}
            )
        }

        composeTestRule.onNodeWithText(text = heroData[0].localizedName).assertIsDisplayed()
        composeTestRule.onNodeWithText(text = heroData[1].localizedName).assertIsDisplayed()
        composeTestRule.onNodeWithText(text = heroData[2].localizedName).assertIsDisplayed()
    }
}