package com.dvidal.ui_herolist.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.request.ImageRequest
import com.dvidal.core.ProgressBarState
import com.dvidal.ui_herolist.components.HeroListItem

@Composable
fun HeroListScreen(
    state: HeroListState,
    imageBuilder: ImageRequest.Builder,
    navigateToDetailScreen: (Int) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(state.heros) { hero ->
                HeroListItem(
                    hero = hero,
                    imageBuilder = imageBuilder,
                    onSelectHero = { navigateToDetailScreen.invoke(it) }
                )
            }
        }
        if (state.pbState is ProgressBarState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}