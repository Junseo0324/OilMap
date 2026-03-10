package com.devhjs.oilmap.presentation.home

sealed interface HomeEvent {
    data class NavigateToStationDetail(val stationId: String) : HomeEvent
    data object NavigateToSettings : HomeEvent
}
