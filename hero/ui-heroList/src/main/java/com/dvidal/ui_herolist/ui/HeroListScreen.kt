@file:OptIn(ExperimentalComposeUiApi::class)

package com.dvidal.ui_herolist.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import coil.request.ImageRequest
import com.dvidal.core.ProgressBarState
import com.dvidal.ui_herolist.components.HeroListItem
import com.dvidal.ui_herolist.components.HeroListToolbar

@Composable
fun HeroListScreen(
    state: HeroListState,
    events: (HeroListEvents) -> Unit,
    imageBuilder: ImageRequest.Builder,
    navigateToDetailScreen: (Int) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            HeroListToolbar(
                heroName = state.heroName,
                onHeroNameChanged = { heroName ->
                    events(HeroListEvents.UpdateHeroName(heroName))
                },
                onExecuteSearch = {
                    events(HeroListEvents.FilterHeros)
                },
                onShowFilterDialog = {}
            )
            LazyColumn {
                items(state.filteredHeros) { hero ->
                    HeroListItem(
                        hero = hero,
                        imageBuilder = imageBuilder,
                        onSelectHero = { navigateToDetailScreen.invoke(it) }
                    )
                }
            }
        }

        if (state.pbState is ProgressBarState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}