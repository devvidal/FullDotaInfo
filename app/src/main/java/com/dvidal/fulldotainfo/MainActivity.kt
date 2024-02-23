package com.dvidal.fulldotainfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.request.ImageRequest
import com.dvidal.constants.Constants
import com.dvidal.fulldotainfo.navigation.Screen
import com.dvidal.fulldotainfo.theme.DotaInfoTheme
import com.dvidal.ui_herodetail.ui.HeroDetailEvents
import com.dvidal.ui_herodetail.ui.HeroDetailScreen
import com.dvidal.ui_herodetail.ui.HeroDetailViewModel
import com.dvidal.ui_herolist.ui.HeroListScreen
import com.dvidal.ui_herolist.ui.HeroListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageBuilder: ImageRequest.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DotaInfoTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.HeroList.route,
                    builder = {

                        addHeroList(
                            navController = navController,
                            imageBuilder = imageBuilder
                        )
                        addHeroDetail(
                            imageBuilder = imageBuilder
                        )

                    }
                )
            }
        }
    }

    private fun NavGraphBuilder.addHeroList(
        navController: NavController,
        imageBuilder: ImageRequest.Builder
    ) {
        composable(
            route = Screen.HeroList.route
        ) {
            val viewModel: HeroListViewModel by viewModels()

            HeroListScreen(
                state = viewModel.state.value,
                imageBuilder = imageBuilder,
                navigateToDetailScreen = { heroId ->
                    navController.navigate("${Screen.HeroDetail.route}/$heroId")
                }
            )
        }
    }

    private fun NavGraphBuilder.addHeroDetail(
        imageBuilder: ImageRequest.Builder
    ) {

        composable(
            route = Screen.HeroDetail.route + "/{${Constants.ARG_HERO_ID}}",
            arguments = Screen.HeroDetail.arguments
        ) {
            val viewModel: HeroDetailViewModel by viewModels()
            val heroId = it.arguments?.getInt(Constants.ARG_HERO_ID) ?: 0
            viewModel.onTriggerEvent(HeroDetailEvents.GetHeroFromCache(heroId))

            HeroDetailScreen(
                state = viewModel.state.value
            )
        }
    }
}

