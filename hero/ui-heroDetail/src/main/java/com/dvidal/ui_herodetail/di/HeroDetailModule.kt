package com.dvidal.ui_herodetail.di

import com.dvidal.core.Logger
import com.dvidal.hero_interactors.GetHeroFromCache
import com.dvidal.hero_interactors.HeroInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeroDetailModule {

    @Provides
    @Singleton
    @Named("heroDetailLogger")
    fun provideLogger(): Logger {
        return Logger(
            tag = "HeroDetail",
            isDebug = true
        )
    }

    @Provides
    @Singleton
    fun provideGetHeroFromCache(
        interactors: HeroInteractors
    ): GetHeroFromCache {
        return interactors.getHeroFromCache
    }
}