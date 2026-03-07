package com.devhjs.oilmap.presentation.home

import androidx.compose.runtime.Immutable
import com.devhjs.oilmap.domain.model.Station


@Immutable
data class HomeState(
    val stations: List<Station> = emptyList(),
    val sortedStations: List<Station> = emptyList(),
    val totalCount: Int = 0,
    val isLoading: Boolean = false,
    val selectedResourceType: String = "휘발유", // 휘발유, 경유, LPG
    val selectedSortOption: String = "거리순" // 가격순, 거리순
)



