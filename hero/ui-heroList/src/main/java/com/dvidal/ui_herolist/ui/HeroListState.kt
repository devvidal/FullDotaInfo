package com.dvidal.ui_herolist.ui

import com.dvidal.core.ProgressBarState
import com.dvidal.core.Queue
import com.dvidal.core.UIComponent
import com.dvidal.core.UiComponentState
import com.dvidal.hero_domain.Hero
import com.dvidal.hero_domain.HeroAttribute
import com.dvidal.hero_domain.HeroFilter

data class HeroListState(
    val pbState: ProgressBarState = ProgressBarState.Idle,
    val heros: List<Hero> = emptyList(),
    val filteredHeros: List<Hero> = listOf(),
    val heroName: String = "",
    val heroFilter: HeroFilter = HeroFilter.Hero(),
    val primaryAttribute: HeroAttribute = HeroAttribute.Unknown,
    val filterDialogState: UiComponentState = UiComponentState.Hide,
    val errorQueue: Queue<UIComponent> = Queue(mutableListOf())
)
