package com.devhjs.oilmap.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavItem(
    val route: Route,
    // TODO: 향후 실제 리소스 R.string 항목으로 대체
    val title: String,
    val icon: ImageVector
) {
    HOME(
        route = Route.Home,
        title = "리스트",
        icon = Icons.AutoMirrored.Filled.List
    ),
    MAP(
        route = Route.Map,
        title = "지도",
        icon = Icons.Default.Place
    )
}
