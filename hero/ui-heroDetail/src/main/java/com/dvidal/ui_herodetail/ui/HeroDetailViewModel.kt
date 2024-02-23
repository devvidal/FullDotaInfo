package com.dvidal.ui_herodetail.ui

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvidal.constants.Constants
import com.dvidal.core.DataState
import com.dvidal.hero_interactors.GetHeroFromCache
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HeroDetailViewModel @Inject constructor(
    private val getHeroFromCache: GetHeroFromCache,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    val state: MutableState<HeroDetailState> = mutableStateOf(HeroDetailState())

    init {
        savedStateHandle.get<Int>(Constants.ARG_HERO_ID)?.let { heroId ->
            onTriggerEvent(HeroDetailEvents.GetHeroFromCache(heroId))
        }
    }

    fun onTriggerEvent(event: HeroDetailEvents) {
        when(event) {
            is HeroDetailEvents.GetHeroFromCache -> getHeroFromCache(event.id)
        }
    }

    private fun getHeroFromCache(id: Int) {
        getHeroFromCache.execute(id).onEach { dataState ->
            when(dataState) {
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.pbState)
                }
                is DataState.Data -> {
                    state.value = state.value.copy(hero = dataState.data)
                }
                is DataState.Response -> {
                    Log.d("HeroDetailViewModel", "Entrou aqui")
                }
            }
        }.launchIn(viewModelScope)
    }
}