package com.dvidal.ui_herodetail.ui

import com.dvidal.core.ProgressBarState
import com.dvidal.core.Queue
import com.dvidal.core.UIComponent
import com.dvidal.hero_domain.Hero

data class HeroDetailState(
    val pbState: ProgressBarState = ProgressBarState.Idle,
    val hero: Hero? = null,
    val errorQueue: Queue<UIComponent> = Queue(mutableListOf())
)