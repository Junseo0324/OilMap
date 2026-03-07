package com.devhjs.oilmap.presentation.map

import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.model.SortType
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
    val selectedOilType: OilType = OilType.GASOLINE,
    val selectedSortType: SortType = SortType.DISTANCE,
    val currentLocation: LatLng? = null,
    val selectedStationId: String? = null
)
