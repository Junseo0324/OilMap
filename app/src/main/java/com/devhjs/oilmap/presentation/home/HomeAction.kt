package com.devhjs.oilmap.presentation.home

import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.model.SortType

sealed interface HomeAction {
    data class OnStationClick(val stationId: String) : HomeAction
    data class OnResourceTypeSelected(val oilType: OilType) : HomeAction
    data class OnSortOptionSelected(val sortType: SortType) : HomeAction
    data object OnPermissionGranted : HomeAction
    data object OnSettingsClick : HomeAction
}