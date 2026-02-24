package com.devhjs.oilmap.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.devhjs.oilmap.presentation.detail.DetailScreenRoot
import com.devhjs.oilmap.presentation.favorite.FavoriteScreen
import com.devhjs.oilmap.presentation.home.Homescreen
import com.devhjs.oilmap.presentation.map.MapScreen

@Composable
fun MainNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Route.Home,
        modifier = Modifier
    ) {
        // Home - List
        composable<Route.Home> {
            Homescreen()
        }
        // Home - Map
        composable<Route.Map> {
            MapScreen()
        }
        // Favorite
        composable<Route.Favorite> {
            FavoriteScreen()
        }
        // Detail
        composable<Route.Detail> { backStackEntry ->
            val detailRoute = backStackEntry.toRoute<Route.Detail>()
            DetailScreenRoot(
                stationId = detailRoute.stationId
            )
        }
    }
}