package com.devhjs.oilmap.presentation.home

sealed interface HomeAction {
    data class OnStationClick(val stationId: String) : HomeAction
    data class OnResourceTypeSelected(val resourceType: String) : HomeAction
    data class OnSortOptionSelected(val sortOption: String) : HomeAction
    data object OnPermissionGranted : HomeAction
    data object OnFavoriteClick : HomeAction
}