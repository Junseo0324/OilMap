package com.devhjs.oilmap.presentation.home

import com.devhjs.oilmap.domain.model.Station

data class GasStationUiModel(
    val station: Station,
    val isLowestPrice: Boolean = false,
    val isOpen: Boolean = true
)

data class HomeState(
    val stations: List<GasStationUiModel> = emptyList(),
    val totalCount: Int = 0,
    val isLoading: Boolean = false,
    val selectedResourceType: String = "휘발유", // 휘발유, 경유, LPG
    val selectedSortOption: String = "가격순" // 가격순, 거리순
)



