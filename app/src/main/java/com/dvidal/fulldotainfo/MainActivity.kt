package com.dvidal.fulldotainfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import coil.ImageLoader
import coil.memory.MemoryCache
import com.dvidal.core.DataState
import com.dvidal.core.Logger
import com.dvidal.core.ProgressBarState
import com.dvidal.core.UIComponent
import com.dvidal.fulldotainfo.theme.DotaInfoTheme
import com.dvidal.hero_interactors.HeroInteractors
import com.dvidal.ui_herolist.ui.HeroListScreen
import com.dvidal.ui_herolist.ui.HeroListState
import com.squareup.sqldelight.android.AndroidSqliteDriver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : ComponentActivity() {

    private val state = mutableStateOf(HeroListState())
    private val progressBarState: MutableState<ProgressBarState> =
        mutableStateOf(ProgressBarState.Idle)
    private lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Init ImageLoader
        imageLoader = ImageLoader.Builder(applicationContext)
            .error(R.drawable.error_image)
            .placeholder(R.drawable.white_background)
            .memoryCache { MemoryCache.Builder(applicationContext).maxSizePercent(.25).build() }
            .crossfade(true)
            .build()

        val getHeros = HeroInteractors.build(
            sqlDriver = AndroidSqliteDriver(
                schema = HeroInteractors.schema,
                context = this,
                name = HeroInteractors.dbName
            )
        ).getHeros
        val logger = Logger("GetHerosTest")
        getHeros.execute().onEach { dataState ->
            when (dataState) {
                is DataState.Data -> {
                    state.value = state.value.copy(heros = dataState.data ?: emptyList())
                }

                is DataState.Loading -> progressBarState.value = dataState.pbState
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> logger.log((dataState.uiComponent as UIComponent.Dialog).description)
                        is UIComponent.None -> logger.log((dataState.uiComponent as UIComponent.None).message)
                    }
                }
            }
        }.launchIn(CoroutineScope(IO))

        setContent {
            DotaInfoTheme {
                HeroListScreen(
                    state = state.value,
                    imageLoader = imageLoader
                )
            }
        }
    }
}