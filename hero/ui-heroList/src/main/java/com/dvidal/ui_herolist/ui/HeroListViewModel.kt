package com.dvidal.ui_herolist.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvidal.core.DataState
import com.dvidal.core.Logger
import com.dvidal.core.UIComponent
import com.dvidal.hero_interactors.GetHeros
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HeroListViewModel @Inject constructor(
    private val getHeros: GetHeros,
    @Named("heroListLogger") private val logger: Logger
): ViewModel() {

    val state = mutableStateOf(HeroListState())

    init {
        onTriggerEvent(HeroListEvents.GetHeros)
    }

    fun onTriggerEvent(event: HeroListEvents) {
        when(event) {
            is HeroListEvents.GetHeros -> getHeros()
            is HeroListEvents.FilterHeros -> filterHeros()
            is HeroListEvents.UpdateHeroName -> updateHeroName(event.heroName)
        }
    }

    private fun getHeros() {

        getHeros.execute().onEach { dataState ->
            when (dataState) {
                is DataState.Data -> {
                    state.value = state.value.copy(heros = dataState.data ?: emptyList())
                    filterHeros()
                }
                is DataState.Loading -> state.value = state.value.copy(pbState = dataState.pbState)
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> logger.log((dataState.uiComponent as UIComponent.Dialog).description)
                        is UIComponent.None -> logger.log((dataState.uiComponent as UIComponent.None).message)
                    }
                }
                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    private fun updateHeroName(heroName: String) {
        state.value = state.value.copy(heroName = heroName)
    }

    private fun filterHeros() {
        val filteredList = state.value.heros.filter {
            it.localizedName.lowercase().contains(state.value.heroName.lowercase())
        }.toMutableList()

        state.value = state.value.copy(filteredHeros = filteredList)
    }
}