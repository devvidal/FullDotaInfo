package com.dvidal.ui_herolist.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvidal.core.DataState
import com.dvidal.core.Logger
import com.dvidal.core.UIComponent
import com.dvidal.hero_domain.HeroAttribute
import com.dvidal.hero_domain.HeroFilter
import com.dvidal.hero_interactors.FilterHeros
import com.dvidal.hero_interactors.GetHeros
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HeroListViewModel @Inject constructor(
    private val getHeros: GetHeros,
    private val filterHeros: FilterHeros,
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
            is HeroListEvents.UpdateHeroFilter -> updateHeroFilter(event.heroFilter)
            is HeroListEvents.UpdateFilterDialogState -> {
                state.value = state.value.copy(filterDialogState = event.uiComponentState)
            }

            is HeroListEvents.UpdateAttributeFilter -> updateAttributeFilter(event.attribute)
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
        val filteredList = filterHeros.execute(
            currentList = state.value.heros,
            heroName = state.value.heroName,
            heroFilter = state.value.heroFilter,
            attributeFilter = state.value.primaryAttribute
        )

        state.value = state.value.copy(filteredHeros = filteredList)
    }

    private fun updateHeroFilter(heroFilter: HeroFilter) {
        state.value = state.value.copy(heroFilter = heroFilter)
        filterHeros()
    }

    private fun updateAttributeFilter(attribute: HeroAttribute) {
        state.value = state.value.copy(primaryAttribute = attribute)
        filterHeros()
    }

}
