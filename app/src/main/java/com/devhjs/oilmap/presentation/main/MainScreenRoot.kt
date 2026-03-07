package com.devhjs.oilmap.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.devhjs.oilmap.core.navigation.MainNavGraph

@Composable
fun MainScreenRoot(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    MainScreen(
        selectedRoute = currentRoute,
        onBottomNavSelected = { item ->
            navController.navigate(item.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        modifier = modifier
    ) { innerPadding ->
        MainNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}