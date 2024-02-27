@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalAnimationApi::class)

package com.dvidal.ui_herolist.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import coil.request.ImageRequest
import com.dvidal.components.DefaultScreenUI
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
    DefaultScreenUI(
        pbState = state.pbState,
        queue = state.errorQueue,
        onRemoveHeadFromQueue = { events(HeroListEvents.OnRemoveHeadFromQueue) }
    ) {
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