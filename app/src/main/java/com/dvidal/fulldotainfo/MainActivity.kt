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
import com.dvidal.fulldotainfo.navigation.Screen
import com.dvidal.fulldotainfo.navigation.Screen.Companion.ARG_HERO_ID
import com.dvidal.fulldotainfo.theme.DotaInfoTheme
import com.dvidal.ui_herodetail.ui.HeroDetail
import com.dvidal.ui_herolist.ui.HeroListScreen
import com.dvidal.ui_herolist.ui.HeroListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: HeroListViewModel by viewModels()

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
                            viewModel = viewModel,
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
}

fun NavGraphBuilder.addHeroList(
    viewModel: HeroListViewModel,
    navController: NavController,
    imageBuilder: ImageRequest.Builder
) {
    composable(
        route = Screen.HeroList.route
    ) {

        HeroListScreen(
            state = viewModel.state.value,
            imageBuilder = imageBuilder,
            navigateToDetailScreen = { heroId ->
                navController.navigate("${Screen.HeroDetail.route}/$heroId")
            }
        )
    }
}

fun NavGraphBuilder.addHeroDetail(
    imageBuilder: ImageRequest.Builder
) {
    composable(
        route = Screen.HeroDetail.route + "/{$ARG_HERO_ID}",
        arguments = Screen.HeroDetail.arguments
    ) {
        HeroDetail( heroId = it.arguments?.getInt(ARG_HERO_ID) )
    }
}