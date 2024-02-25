package com.dvidal.ui_herolist.ui

import com.dvidal.core.UiComponentState
import com.dvidal.hero_domain.HeroFilter

sealed class HeroListEvents {

    data object GetHeros: HeroListEvents()
    data object FilterHeros: HeroListEvents()
    data class UpdateHeroName(
        val heroName: String
    ): HeroListEvents()

    data class UpdateHeroFilter(
        val heroFilter: HeroFilter
    ): HeroListEvents()

    data class UpdateFilterDialogState(
        val uiComponentState: UiComponentState
    ): HeroListEvents()
}