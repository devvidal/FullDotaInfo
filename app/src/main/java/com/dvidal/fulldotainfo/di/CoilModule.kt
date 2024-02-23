package com.dvidal.fulldotainfo.di

import android.app.Application
import coil.request.ImageRequest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoilModule {

    @Provides
    @Singleton
    fun provideImageRequestBuilder(app: Application): ImageRequest.Builder {
        return ImageRequest.Builder(app)
    }
}