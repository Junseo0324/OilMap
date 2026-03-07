package com.devhjs.oilmap.presentation.favorite

import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.model.Station

sealed interface FavoriteAction {
    data class OnStationClick(val stationId: String) : FavoriteAction
    data class OnResourceTypeSelected(val oilType: OilType) : FavoriteAction
    data class OnToggleFavorite(val station: Station) : FavoriteAction
    data object OnBackClick : FavoriteAction
}
