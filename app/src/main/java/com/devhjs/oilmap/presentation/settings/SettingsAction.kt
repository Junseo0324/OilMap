package com.devhjs.oilmap.presentation.settings

import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.model.SortType

sealed interface SettingsAction {
    data class OnFavoriteOilTypeChanged(val oilType: OilType) : SettingsAction
    data class OnDefaultSortTypeChanged(val sortType: SortType) : SettingsAction
    data class OnSearchRadiusChanged(val radius: Int) : SettingsAction
    data object OnResetPreferences : SettingsAction
    data object OnBackClick : SettingsAction
}
