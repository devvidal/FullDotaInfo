package com.dvidal.ui_herolist.ui

import com.dvidal.core.ProgressBarState
import com.dvidal.hero_domain.Hero

data class HeroListState(
    val pbState: ProgressBarState = ProgressBarState.Idle,
    val heros: List<Hero> = emptyList()
)
