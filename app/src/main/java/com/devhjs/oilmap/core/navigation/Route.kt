package com.devhjs.oilmap.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Home : Route

    @Serializable
    data object Map : Route

    @Serializable
    data object Favorite : Route

    @Serializable
    data class Detail(val stationId: String) : Route

    @Serializable
    data object Settings : Route
}
