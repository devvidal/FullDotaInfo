package com.dvidal.hero_domain

import com.dvidal.core.FilterOrder

sealed class HeroFilter(
    val uiValue: String
) {

    data class Hero(
        val order: FilterOrder = FilterOrder.Ascending
    ): HeroFilter("Hero")

    data class ProWins(
        val order: FilterOrder = FilterOrder.Descending
    ): HeroFilter("Pro win-rate")
}