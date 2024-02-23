package com.dvidal.fulldotainfo.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val arguments: List<NamedNavArgument>
) {

    data object HeroList: Screen(
        route = "heroList",
        arguments = emptyList()
    )

    data object HeroDetail: Screen(
        route = "heroDetail",
        arguments = listOf(
            navArgument(ARG_HERO_ID) {
                type = NavType.IntType
            }
        )
    )

    companion object {

        const val ARG_HERO_ID = "heroId"
    }
}