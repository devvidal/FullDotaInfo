package com.dvidal.hero_interactors

import com.dvidal.core.DataState
import com.dvidal.core.ProgressBarState
import com.dvidal.core.UIComponent
import com.dvidal.hero_datasource.cache.HeroCache
import com.dvidal.hero_domain.Hero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetHeroFromCache(
    private val cache: HeroCache
) {

    fun execute(id: Int): Flow<DataState<Hero>> = flow {
        try {
            emit(DataState.Loading(pbState = ProgressBarState.Loading))

            val cachedHero = cache.getHero(id) ?: throw Exception("That hero does not exist in the cache.")
            emit(DataState.Data(cachedHero))

        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                DataState.Response(
                    uiComponent = UIComponent.Dialog(
                        title = "Error",
                        description = e.message ?: "Unknown Error"
                    )
                )
            )
        } finally { emit(DataState.Loading(ProgressBarState.Idle)) }
    }
}