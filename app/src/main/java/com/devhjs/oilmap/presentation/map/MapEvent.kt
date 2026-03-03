package com.devhjs.oilmap.presentation.map

sealed interface MapEvent {
    data class NavigateToStationDetail(val stationId: String) : MapEvent
}
