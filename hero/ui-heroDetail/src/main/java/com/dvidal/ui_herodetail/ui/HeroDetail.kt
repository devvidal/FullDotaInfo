package com.dvidal.ui_herodetail.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun HeroDetail(
    heroId: Int?
) {

    Text(text = "Hero id is $heroId")
}