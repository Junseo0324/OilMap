package com.devhjs.oilmap.presentation.favorite

import androidx.compose.runtime.Immutable
import com.devhjs.oilmap.domain.model.Station

@Immutable
data class FavoriteState(
    val stations: List<Station> = emptyList(),
    val totalCount: Int = 0,
    val isLoading: Boolean = false,
    val selectedResourceType: String = "휘발유"
)
