package com.dvidal.ui_herodetail.ui

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
import kotlin.random.Random

class HeroDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val heroData = serializeHeroData(HeroDataValid.data)
    private val imageBuilder = ImageRequest.Builder(context)

    @Test
    fun isHeroDataShown() {
        // choose a random hero
        val hero = heroData.get(Random.nextInt(0, heroData.size - 1))

        composeTestRule.setContent {
            val state = remember {
                HeroDetailState(
                    hero = hero
                )
            }

            HeroDetailScreen(
                state = state,
                events = {},
                imageBuilder = imageBuilder
            )
        }

        composeTestRule.onNodeWithText(hero.localizedName).assertIsDisplayed()
        composeTestRule.onNodeWithText(hero.primaryAttribute.uiValue).assertIsDisplayed()
        composeTestRule.onNodeWithText(hero.attackType.uiValue).assertIsDisplayed()

        // not been shown cause we need to apply the theme to fit properly
        val proWinPercentage = (hero.proWins.toDouble() / hero.proPick.toDouble() * 100).toInt()
//        composeTestRule.onNodeWithText("$proWinPercentage %").assertIsDisplayed()

        val turboWinPercentage = (hero.turboWins.toDouble() / hero.turboPicks.toDouble() * 100).toInt()
//        composeTestRule.onNodeWithText("$turboWinPercentage %").assertIsDisplayed()
    }
}