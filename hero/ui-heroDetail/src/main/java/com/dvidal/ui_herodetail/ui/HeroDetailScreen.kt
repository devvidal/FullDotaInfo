package com.dvidal.ui_herodetail.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun HeroDetailScreen(
    state: HeroDetailState
) {

    Text(text = "Hero name is ${state.hero?.localizedName} ")
}