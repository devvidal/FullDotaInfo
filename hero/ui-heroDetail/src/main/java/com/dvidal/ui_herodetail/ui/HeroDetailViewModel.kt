package com.dvidal.ui_herodetail.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvidal.constants.Constants
import com.dvidal.core.DataState
import com.dvidal.core.Logger
import com.dvidal.core.Queue
import com.dvidal.core.UIComponent
import com.dvidal.hero_interactors.GetHeroFromCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class HeroDetailViewModel @Inject constructor(
    private val getHeroFromCache: GetHeroFromCache,
    @Named("heroDetailLogger") private val logger: Logger,
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
            is HeroDetailEvents.OnRemoveHeadFromQueue -> removeHeadFromQueue()
        }
    }

    private fun getHeroFromCache(id: Int) {
        getHeroFromCache.execute(id).onEach { dataState ->
            when(dataState) {
                is DataState.Loading -> {
                    state.value = state.value.copy(pbState = dataState.pbState)
                }
                is DataState.Data -> {
                    state.value = state.value.copy(hero = dataState.data)
                }
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> {
                            appendToMessageQueue(dataState.uiComponent)
                        }
                        is UIComponent.None -> logger.log((dataState.uiComponent as UIComponent.None).message)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun appendToMessageQueue(uiComponent: UIComponent) {
        val queue = state.value.errorQueue
        queue.add(uiComponent)
        state.value = state.value.copy(errorQueue = Queue(mutableListOf())) // force recompose
        state.value = state.value.copy(errorQueue = queue)
    }

    private fun removeHeadFromQueue() {
        try {
            val queue = state.value.errorQueue
            queue.remove()

            state.value = state.value.copy(errorQueue = Queue(mutableListOf())) // force recompose
            state.value = state.value.copy(errorQueue = queue)
        } catch (e: Exception) {
            logger.log("Nothing to remove from DialogQueue")
        }
    }
}