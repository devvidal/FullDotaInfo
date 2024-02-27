package com.dvidal.hero_interactors

import com.dvidal.hero_datasource.cache.HeroCache
import com.dvidal.hero_datasource.cache.HeroDatabase
import com.dvidal.hero_datasource.network.HeroService
import com.squareup.sqldelight.db.SqlDriver

data class HeroInteractors(
    val getHeros: GetHeros,
    val getHeroFromCache: GetHeroFromCache,
    val filterHeros: FilterHeros
) {

    companion object Factory {

        val schema: SqlDriver.Schema = HeroDatabase.Schema
        const val dbName: String = "heros.db"
        fun build(sqlDriver: SqlDriver): HeroInteractors {
            val service = HeroService.build()
            val cache = HeroCache.build(sqlDriver)

            return HeroInteractors(
                getHeros = GetHeros(cache, service),
                getHeroFromCache = GetHeroFromCache(cache),
                filterHeros = FilterHeros()
            )
        }
    }
}