package com.devhjs.oilmap.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.devhjs.oilmap.core.navigation.Route
import com.devhjs.oilmap.presentation.detail.DetailScreenRoot
import com.devhjs.oilmap.presentation.favorite.FavoriteScreenRoot
import com.devhjs.oilmap.presentation.home.HomeScreenRoot
import com.devhjs.oilmap.presentation.map.MapScreenRoot

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onNavigateToDetail: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Route.Home,
        modifier = modifier
    ) {
        // Home - List
        composable<Route.Home> {
            HomeScreenRoot(
                onNavigateToDetail = onNavigateToDetail,
                onNavigateToFavorite = { navController.navigate(Route.Favorite) }
            )
        }
        // Home - Map
        composable<Route.Map> {
            MapScreenRoot(
                onNavigateToDetail = onNavigateToDetail
            )
        }
        // Favorite 
        composable<Route.Favorite> {
            FavoriteScreenRoot(
                onNavigateToDetail = onNavigateToDetail,
                onNavigateBack = onNavigateBack
            )
        }
        // Detail 
        composable<Route.Detail> { backStackEntry ->
            val detailRoute = backStackEntry.toRoute<Route.Detail>()
            DetailScreenRoot(
                stationId = detailRoute.stationId,
                onBack = onNavigateBack
            )
        }
    }
}