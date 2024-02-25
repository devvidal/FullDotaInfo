@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalAnimationApi::class)

package com.dvidal.ui_herolist.ui

import androidx.compose.animation.ExperimentalAnimationApi
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
import com.dvidal.core.UiComponentState
import com.dvidal.ui_herolist.components.HeroListFilter
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
                onShowFilterDialog = {
                    events(HeroListEvents.UpdateFilterDialogState(UiComponentState.Show))
                }
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

        if (state.filterDialogState is UiComponentState.Show) {
            HeroListFilter(
                heroFilter = state.heroFilter,
                attributeFilter = state.primaryAttribute,
                onUpdateHeroFilter = { events(HeroListEvents.UpdateHeroFilter(it)) },
                onUpdateAttributeFilter = { events(HeroListEvents.UpdateAttributeFilter(it)) },
                onCloseDialog = {
                    events(HeroListEvents.UpdateFilterDialogState(UiComponentState.Hide))
                }
            )
        }
    }
}