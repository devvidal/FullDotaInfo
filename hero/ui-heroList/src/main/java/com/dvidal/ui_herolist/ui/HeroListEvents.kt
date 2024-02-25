package com.dvidal.ui_herolist.ui

sealed class HeroListEvents {

    data object GetHeros: HeroListEvents()
    data object FilterHeros: HeroListEvents()
    data class UpdateHeroName(
        val heroName: String
    ): HeroListEvents()
}