package com.devhjs.oilmap.presentation.map

import com.devhjs.oilmap.domain.model.Station
import com.google.android.gms.maps.model.LatLng

data class MapStationUiModel(
    val station: Station,
    val latLng: LatLng,
    val isLowestPrice: Boolean = false
)

data class MapState(
    val stations: List<MapStationUiModel> = emptyList(),
    val totalCount: Int = 0,
    val isLoading: Boolean = false,
    val selectedResourceType: String = "휘발유",
    val selectedSortOption: String = "가격순",
    val currentLocation: LatLng? = null,
    val selectedStationId: String? = null
)
