package com.devhjs.oilmap.core.navigation

import com.devhjs.oilmap.R

data class BottomNavItem(
    val route: Route,
    val routeName: String,
    val title: String,
    val icon: Int
) {
    companion object {
        val items = listOf(
            BottomNavItem(
                route = Route.Home,
                routeName = Route.Home::class.qualifiedName.orEmpty(),
                title = "리스트",
                icon = R.drawable.map
            ),
            BottomNavItem(
                route = Route.Map,
                routeName = Route.Map::class.qualifiedName.orEmpty(),
                title = "지도",
                icon = R.drawable.list
            ),
            BottomNavItem(
                route = Route.Favorite,
                routeName = Route.Favorite::class.qualifiedName.orEmpty(),
                title = "즐겨찾기",
                icon = R.drawable.star_filled
            )
        )
    }
}
