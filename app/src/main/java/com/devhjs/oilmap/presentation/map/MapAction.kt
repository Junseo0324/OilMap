package com.devhjs.oilmap.presentation.map

import com.devhjs.oilmap.domain.model.OilType

sealed interface MapAction {
    data class OnStationClick(val stationId: String) : MapAction
    data class OnResourceTypeSelected(val oilType: OilType) : MapAction
    data object OnPermissionGranted : MapAction
    data class OnMarkerClick(val stationId: String) : MapAction
    data object OnMapLoaded : MapAction
}
