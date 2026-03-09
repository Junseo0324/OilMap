package com.devhjs.oilmap.presentation.map

import androidx.compose.runtime.Immutable
import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.model.Station
import com.google.android.gms.maps.model.LatLng

@Immutable
data class MapStationUiModel(
    val station: Station,
    val latLng: LatLng,
    val isLowestPrice: Boolean = false
)

@Immutable
data class MapState(
    val stations: List<MapStationUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val selectedOilType: OilType = OilType.GASOLINE,
    val currentLocation: LatLng? = null,
    val selectedStationId: String? = null,
    val isMapLoaded: Boolean = false
)
