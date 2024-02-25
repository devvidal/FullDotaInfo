package com.dvidal.hero_interactors

import com.dvidal.core.FilterOrder
import com.dvidal.hero_domain.Hero
import com.dvidal.hero_domain.HeroAttribute
import com.dvidal.hero_domain.HeroFilter
import kotlin.math.round

class FilterHeros {

    fun execute(
        currentList: List<Hero>,
        heroName: String,
        heroFilter: HeroFilter,
        attributeFilter: HeroAttribute
    ): List<Hero> {
        var filteredList = currentList.filter {
            it.localizedName.lowercase().contains(heroName.lowercase())
        }.toMutableList()

        when(heroFilter) {
            is HeroFilter.Hero -> {
                when(heroFilter.order) {
                    FilterOrder.Ascending -> filteredList.sortBy { it.localizedName }
                    FilterOrder.Descending -> filteredList.sortByDescending { it.localizedName }
                }
            }
            is HeroFilter.ProWins -> {
                when(heroFilter.order) {
                    FilterOrder.Ascending -> filteredList.sortBy {
                        getWinRate(it.proWins.toDouble(), it.proPick.toDouble())
                    }
                    FilterOrder.Descending -> filteredList.sortByDescending {
                        getWinRate(it.proWins.toDouble(), it.proPick.toDouble())
                    }
                }
            }
        }

        when(attributeFilter) {
            HeroAttribute.Agility -> {
                filteredList = filteredList.filter { it.primaryAttribute is HeroAttribute.Agility }.toMutableList()
            }
            HeroAttribute.Intelligence -> {
                filteredList = filteredList.filter { it.primaryAttribute is HeroAttribute.Intelligence }.toMutableList()
            }
            HeroAttribute.Strength -> {
                filteredList = filteredList.filter { it.primaryAttribute is HeroAttribute.Strength }.toMutableList()
            }
            HeroAttribute.Unknown -> {
                // do not filter
            }
        }

        return filteredList
    }

    private fun getWinRate(proWins: Double, proPick: Double): Int {
        return if (proPick <= 0)
            0
        else round(proWins / proPick * 100).toInt()
    }
}