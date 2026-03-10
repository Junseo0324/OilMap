package com.devhjs.oilmap.presentation.settings

import androidx.compose.runtime.Immutable
import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.model.SortType

@Immutable
data class SettingsState(
    val favoriteOilType: OilType = OilType.GASOLINE,
    val defaultSortType: SortType = SortType.DISTANCE,
    val searchRadius: Int = 3000,
    val isLoading: Boolean = false
)
