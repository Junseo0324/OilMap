package com.devhjs.oilmap.core.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun MainBottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomTabs = BottomNavItem.entries.toTypedArray()
    val isBottomBarVisible = bottomTabs.any {
        currentDestination?.hasRoute(it.route::class) == true
    }

    if (isBottomBarVisible) {
        NavigationBar {
            bottomTabs.forEach { item ->
                NavigationBarItem(
                    selected = currentDestination?.hasRoute(item.route::class) == true,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(imageVector = item.icon, contentDescription = item.title)
                    },
                    label = {
                        Text(text = item.title)
                    }
                )
            }
        }
    }
}