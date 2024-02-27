package com.dvidal.hero_interactors

import com.dvidal.core.DataState
import com.dvidal.core.Logger
import com.dvidal.core.ProgressBarState
import com.dvidal.core.UIComponent
import com.dvidal.hero_datasource.cache.HeroCache
import com.dvidal.hero_datasource.network.HeroService
import com.dvidal.hero_domain.Hero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetHeros(
    private val cache: HeroCache,
    private val service: HeroService
) {

    fun execute(): Flow<DataState<List<Hero>>> = flow {
        try {
            emit(DataState.Loading(pbState = ProgressBarState.Loading))
            val heros: List<Hero> = try {
                service.fetchHeroStats()
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    DataState.Response(
                        uiComponent = UIComponent.Dialog(
                            title = "Network Data Error",
                            description = e.message ?: "Unknown Error"
                        )
                    )
                )
                emptyList()
            }

            // cache network data
            cache.insert(heros)

            // emit data from cache
            val cachedHeroes = cache.selectAll()
            emit(DataState.Data(cachedHeroes))

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