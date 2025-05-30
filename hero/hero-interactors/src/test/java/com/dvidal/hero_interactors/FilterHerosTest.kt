package com.dvidal.hero_interactors

import com.dvidal.core.FilterOrder
import com.dvidal.hero_datasource_test.network.data.HeroDataValid
import com.dvidal.hero_datasource_test.network.serializeHeroData
import com.dvidal.hero_domain.HeroAttribute
import com.dvidal.hero_domain.HeroFilter
import org.junit.Test
import kotlin.math.round

/**
 * 1. Success (Search for specific hero by 'localizedName' param)
 * 2. Success (Order by 'localizedName' param DESC)
 * 3. Success (Order by 'localizedName' param ASC)
 * 4. Success (Order by 'proWins' % ASC)
 * 5. Success (Order by 'proWins' % DESC)
 * 6. Success (Filter by 'HeroAttribute' "Strength")
 * 7. Success (Filter by 'HeroAttribute' "Agility")
 * 8. Success (Filter by 'HeroAttribute' "Intelligence")
 */
class FilterHerosTest {

    // System in test
    private lateinit var filterHeros: FilterHeros

    // Data
    private val heroList = serializeHeroData(HeroDataValid.data)

    @Test
    fun searchHeroByName() {
        filterHeros = FilterHeros()

        // Execute use-case
        val emissions = filterHeros.execute(
            currentList = heroList,
            heroName = "Slark",
            heroFilter = HeroFilter.Hero(),
            attributeFilter = HeroAttribute.Unknown,
        )

        // confirm it returns a single hero
        assert(emissions[0].localizedName == "Slark")
    }

    @Test
    fun orderByNameDesc() {
        filterHeros = FilterHeros()

        // Execute use-case
        val emissions = filterHeros.execute(
            currentList = heroList,
            heroName = "",
            heroFilter = HeroFilter.Hero(order = FilterOrder.Descending),
            attributeFilter = HeroAttribute.Unknown,
        )

        // confirm they are ordered Z-A
        for (index in 1..<emissions.size) {
            assert(emissions[index - 1].localizedName.toCharArray()[0] >= emissions[index].localizedName.toCharArray()[0])
        }
    }

    @Test
    fun orderByNameAsc() {
        filterHeros = FilterHeros()

        // Execute use-case
        val emissions = filterHeros.execute(
            currentList = heroList,
            heroName = "",
            heroFilter = HeroFilter.Hero(order = FilterOrder.Ascending),
            attributeFilter = HeroAttribute.Unknown,
        )

        // confirm they are ordered A-Z
        for (index in 1..<emissions.size) {
            assert(emissions[index - 1].localizedName.toCharArray()[0] <= emissions[index].localizedName.toCharArray()[0])
        }
    }

    @Test
    fun orderByProWinsDesc() {
        filterHeros = FilterHeros()

        // Execute use-case
        val emissions = filterHeros.execute(
            currentList = heroList,
            heroName = "",
            heroFilter = HeroFilter.ProWins(order = FilterOrder.Descending),
            attributeFilter = HeroAttribute.Unknown,
        )

        // confirm they are ordered highest to lowest
        for (index in 1..<emissions.size) {
            val prevWinPercentage =
                round(emissions[index - 1].proWins.toDouble() / emissions[index - 1].proPick.toDouble() * 100).toInt()
            val currWinPercentage =
                round(emissions[index].proWins.toDouble() / emissions[index].proPick.toDouble() * 100).toInt()

            assert(prevWinPercentage >= currWinPercentage)
        }
    }

    @Test
    fun orderByProWinsAsc() {
        filterHeros = FilterHeros()

        // Execute use-case
        val emissions = filterHeros.execute(
            currentList = heroList,
            heroName = "",
            heroFilter = HeroFilter.ProWins(order = FilterOrder.Ascending),
            attributeFilter = HeroAttribute.Unknown,
        )

        // confirm they are ordered lowest to highest
        for (index in 1..<emissions.size) {
            val prevWinPercentage =
                round(emissions[index - 1].proWins.toDouble() / emissions[index - 1].proPick.toDouble() * 100).toInt()
            val currWinPercentage =
                round(emissions[index].proWins.toDouble() / emissions[index].proPick.toDouble() * 100).toInt()

            assert(prevWinPercentage <= currWinPercentage)
        }
    }

    @Test
    fun filterByStrength() {
        filterHeros = FilterHeros()

        // Execute use-case
        val emissions = filterHeros.execute(
            currentList = heroList,
            heroName = "",
            heroFilter = HeroFilter.Hero(),
            attributeFilter = HeroAttribute.Strength,
        )

        for (hero in emissions) {
            assert(hero.primaryAttribute is HeroAttribute.Strength)
        }
    }

    @Test
    fun filterByAgility() {
        filterHeros = FilterHeros()

        // Execute use-case
        val emissions = filterHeros.execute(
            currentList = heroList,
            heroName = "",
            heroFilter = HeroFilter.Hero(),
            attributeFilter = HeroAttribute.Agility,
        )

        for (hero in emissions) {
            assert(hero.primaryAttribute is HeroAttribute.Agility)
        }
    }

    @Test
    fun filterByIntelligence() {
        filterHeros = FilterHeros()

        // Execute use-case
        val emissions = filterHeros.execute(
            currentList = heroList,
            heroName = "",
            heroFilter = HeroFilter.Hero(),
            attributeFilter = HeroAttribute.Intelligence,
        )

        for (hero in emissions) {
            assert(hero.primaryAttribute is HeroAttribute.Intelligence)
        }
    }
}