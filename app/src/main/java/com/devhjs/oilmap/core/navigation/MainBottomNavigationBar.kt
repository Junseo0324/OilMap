package com.devhjs.oilmap.core.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource

@Composable
fun MainBottomNavigationBar(
    selectedRoute: String?,
    onNavigate: (BottomNavItem) -> Unit
) {
    val isBottomBarVisible = BottomNavItem.items.any {
        selectedRoute == it.routeName
    }

    if (isBottomBarVisible) {
        NavigationBar {
            BottomNavItem.items.forEach { item ->
                NavigationBarItem(
                    selected = selectedRoute == item.routeName,
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