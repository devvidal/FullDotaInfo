package com.dvidal.ui_herodetail.ui

import com.dvidal.core.ProgressBarState
import com.dvidal.hero_domain.Hero

data class HeroDetailState(
    val pbState: ProgressBarState = ProgressBarState.Idle,
    val hero: Hero? = null
) {


}