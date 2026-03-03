package com.devhjs.oilmap.presentation.map


sealed interface MapAction {
    data class OnStationClick(val stationId: String) : MapAction
    data class OnResourceTypeSelected(val resourceType: String) : MapAction
    data class OnSortOptionSelected(val sortOption: String) : MapAction
    data object OnPermissionGranted : MapAction
    data class OnMarkerClick(val stationId: String) : MapAction
}
