package com.devhjs.oilmap.presentation.home

import androidx.compose.runtime.Immutable
import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.model.SortType
import com.devhjs.oilmap.domain.model.Station

@Immutable
data class HomeState(
    val stations: List<Station> = emptyList(),
    val sortedStations: List<Station> = emptyList(),
    val totalCount: Int = 0,
    val isLoading: Boolean = false,
    val isPreferencesLoaded: Boolean = false,
    val selectedOilType: OilType = OilType.GASOLINE,
    val selectedSortType: SortType = SortType.DISTANCE,
    val searchRadius: Int = 3000
)
