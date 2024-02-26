package com.dvidal.hero_interactors

import com.dvidal.core.DataState
import com.dvidal.core.ProgressBarState
import com.dvidal.core.UIComponent
import com.dvidal.hero_datasource_test.cache.HeroCacheFake
import com.dvidal.hero_datasource_test.cache.HeroDatabaseFake
import com.dvidal.hero_datasource_test.network.HeroServiceFake
import com.dvidal.hero_datasource_test.network.HeroServiceResponseType
import com.dvidal.hero_datasource_test.network.data.HeroDataValid
import com.dvidal.hero_datasource_test.network.data.HeroDataValid.NUM_HEROS
import com.dvidal.hero_datasource_test.network.serializeHeroData
import com.dvidal.hero_domain.Hero
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetHeroTest {

    // system in test
    private lateinit var getHeros: GetHeros

    @Test
    fun getHeros_validData_success() = runBlocking {
        // setup
        val heroDatabase = HeroDatabaseFake()
        val heroCache = HeroCacheFake(heroDatabase)
        val heroService = HeroServiceFake.build(
            type = HeroServiceResponseType.ValidData
        )

        getHeros = GetHeros(
            cache = heroCache,
            service = heroService
        )

        // confirm that the cache is empty, before any use-case has been executed
        var cachedHeros = heroCache.selectAll()
        assert(cachedHeros.isEmpty())

        // execute the use-case
        val emissions = getHeros.execute().toList()

        // first emission should be loading
        assert(emissions.first() == DataState.Loading<List<Hero>>(ProgressBarState.Loading))

        // second emission is data
        assert(emissions[1] is DataState.Data)
        assert(((emissions[1] as DataState.Data).data?.size ?: 0) == NUM_HEROS)

        // confirm the cache is no longer empty
        cachedHeros = heroCache.selectAll()
        assert(cachedHeros.size == NUM_HEROS)

        // third emission should be loading idle
        assert(emissions[2] == DataState.Loading<List<Hero>>(ProgressBarState.Idle))
    }

    @Test
    fun getHeros_malformedData_successFromCache() = runBlocking {
        // setup
        val heroDatabase = HeroDatabaseFake()
        val heroCache = HeroCacheFake(heroDatabase)
        val heroService = HeroServiceFake.build(
            type = HeroServiceResponseType.MalformedData
        )

        getHeros = GetHeros(
            cache = heroCache,
            service = heroService
        )

        // confirm that the cache is empty, before any use-case has been executed
        var cachedHeros = heroCache.selectAll()
        assert(cachedHeros.isEmpty())

        // add some data to the cache
        val heroData = serializeHeroData(HeroDataValid.data)
        heroCache.insert(heroData)

        cachedHeros = heroCache.selectAll()
        assert(cachedHeros.isNotEmpty())

        // execute the use-case
        val emissions = getHeros.execute().toList()

        // first emission should be loading
        assert(emissions.first() == DataState.Loading<List<Hero>>(ProgressBarState.Loading))

        // second emission is an error response
        assert(emissions[1] is DataState.Response)
        assert(((emissions[1] as DataState.Response).uiComponent as UIComponent.Dialog).title == "Network Data Error")
        assert(((emissions[1] as DataState.Response).uiComponent as UIComponent.Dialog).description.contains("Unexpected JSON token at offset"))

        // confirm third emission is data from the cache
        assert(emissions[2] is DataState.Data)
        assert(((emissions[2] as DataState.Data).data?.size ?: 0) == NUM_HEROS)

        // forth emission should be loading idle
        assert(emissions[3] == DataState.Loading<List<Hero>>(ProgressBarState.Idle))
    }

    @Test
    fun getHeros_emptyData() = runBlocking {
        // setup
        val heroDatabase = HeroDatabaseFake()
        val heroCache = HeroCacheFake(heroDatabase)
        val heroService = HeroServiceFake.build(
            type = HeroServiceResponseType.EmptyData
        )

        getHeros = GetHeros(
            cache = heroCache,
            service = heroService
        )

        // confirm that the cache is empty, before any use-case has been executed
        var cachedHeros = heroCache.selectAll()
        assert(cachedHeros.isEmpty())

        // execute the use-case
        val emissions = getHeros.execute().toList()

        // first emission should be loading
        assert(emissions.first() == DataState.Loading<List<Hero>>(ProgressBarState.Loading))

        // second emission is data
        assert(emissions[1] is DataState.Data)
        assert(((emissions[1] as DataState.Data).data?.size ?: 0) == 0)

        // confirm the cache is no longer empty
        cachedHeros = heroCache.selectAll()
        assert(cachedHeros.isEmpty())

        // third emission should be loading idle
        assert(emissions[2] == DataState.Loading<List<Hero>>(ProgressBarState.Idle))
    }
}