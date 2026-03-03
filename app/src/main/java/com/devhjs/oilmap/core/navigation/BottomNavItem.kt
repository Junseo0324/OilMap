package com.devhjs.oilmap.core.navigation

import com.devhjs.oilmap.R

enum class BottomNavItem(
    val route: Route,
    val title: String,
    val icon: Int
) {
    HOME(
        route = Route.Home,
        title = "리스트",
        icon = R.drawable.map
    ),
    MAP(
        route = Route.Map,
        title = "지도",
        icon = R.drawable.list
    )
}
