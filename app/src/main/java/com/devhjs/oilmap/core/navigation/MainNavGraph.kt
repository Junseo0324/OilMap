package com.devhjs.oilmap.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.devhjs.oilmap.presentation.detail.DetailScreenRoot
import com.devhjs.oilmap.presentation.favorite.FavoriteScreenRoot
import com.devhjs.oilmap.presentation.home.HomeScreenRoot
import com.devhjs.oilmap.presentation.map.MapScreenRoot

@Composable
fun MainNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Route.Home,
        modifier = modifier
    ) {
        composable<Route.Home> {
            HomeScreenRoot(
                onNavigateToDetail = { stationId ->
                    navController.navigate(Route.Detail(stationId))
                },
                onNavigateToFavorite = { navController.navigate(Route.Favorite) }
            )
        }
        // Home - Map
        composable<Route.Map> {
            MapScreenRoot(
                onNavigateToDetail = { stationId ->
                    navController.navigate(Route.Detail(stationId))
                }
            )
        }
        // Favorite
        composable<Route.Favorite> {
            FavoriteScreenRoot(
                onNavigateToDetail = { stationId ->
                    navController.navigate(Route.Detail(stationId))
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        // Detail
        composable<Route.Detail> {
            DetailScreenRoot(
                onBack = { navController.popBackStack() }
            )
        }
    }
}