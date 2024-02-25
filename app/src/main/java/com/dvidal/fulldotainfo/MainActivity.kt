@file:OptIn(ExperimentalAnimationApi::class)

package com.dvidal.fulldotainfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.request.ImageRequest
import com.dvidal.constants.Constants
import com.dvidal.fulldotainfo.navigation.Screen
import com.dvidal.fulldotainfo.theme.DotaInfoTheme
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

                BoxWithConstraints {
                    val boxWithConstraintsScope = this

                    NavHost(
                        navController = navController,
                        startDestination = Screen.HeroList.route,
                        builder = {

                            addHeroList(
                                navController = navController,
                                imageBuilder = imageBuilder,
                                width = constraints.maxWidth / 2
                            )
                            addHeroDetail(
                                imageBuilder = imageBuilder,
                                width = constraints.maxWidth / 2
                            )

                        }
                    )
                }
            }
        }
    }

    private fun NavGraphBuilder.addHeroList(
        navController: NavController,
        imageBuilder: ImageRequest.Builder,
        width: Int
    ) {
        composable(
            route = Screen.HeroList.route,
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -width },
                    animationSpec = tween(
                        durationMillis = 300
                    )
                ) + fadeOut(
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -width },
                    animationSpec = tween(
                        durationMillis = 300
                    )
                ) + fadeIn(
                    animationSpec = tween(300)
                )
            }
        ) {
            val viewModel: HeroListViewModel by viewModels()

            HeroListScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
                imageBuilder = imageBuilder,
                navigateToDetailScreen = { heroId ->
                    navController.navigate("${Screen.HeroDetail.route}/$heroId")
                }
            )
        }
    }

    private fun NavGraphBuilder.addHeroDetail(
        imageBuilder: ImageRequest.Builder,
        width: Int
    ) {

        composable(
            route = Screen.HeroDetail.route + "/{${Constants.ARG_HERO_ID}}",
            arguments = Screen.HeroDetail.arguments,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { width },
                    animationSpec = tween(
                        durationMillis = 300
                    )
                ) + fadeIn(
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { width },
                    animationSpec = tween(
                        durationMillis = 300
                    )
                ) + fadeOut(
                    animationSpec = tween(300)
                )
            }
        ) {

            // We need to use hiltViewModel() to be able to use SavedStateHandle properly
            val viewModel: HeroDetailViewModel = hiltViewModel()

            HeroDetailScreen(
                state = viewModel.state.value,
                imageBuilder = imageBuilder
            )
        }
    }
}

