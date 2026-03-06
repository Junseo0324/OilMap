package com.devhjs.oilmap.core.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute

@Composable
fun MainBottomNavigationBar(
    currentDestination: NavDestination?,
    onNavigate: (BottomNavItem) -> Unit
) {
    val bottomTabs = BottomNavItem.entries.toTypedArray()
    val isBottomBarVisible = bottomTabs.any {
        currentDestination?.hasRoute(it.route::class) == true
    }

    if (isBottomBarVisible) {
        NavigationBar {
            bottomTabs.forEach { item ->
                NavigationBarItem(
                    selected = currentDestination?.hasRoute(item.route::class) == true,
                    onClick = { onNavigate(item) },
                    icon = {
                        Icon(painter = painterResource(item.icon), contentDescription = item.title)
                    },
                    label = {
                        Text(text = item.title)
                    }
                )
            }
        }
    }
}