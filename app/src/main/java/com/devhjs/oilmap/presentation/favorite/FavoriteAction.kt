package com.devhjs.oilmap.presentation.favorite

import com.devhjs.oilmap.domain.model.Station

// 즐겨찾기 화면에서 사용자가 수행할 수 있는 액션 정의
sealed interface FavoriteAction {
    data class OnStationClick(val stationId: String) : FavoriteAction
    data class OnResourceTypeSelected(val resourceType: String) : FavoriteAction
    data class OnToggleFavorite(val station: Station) : FavoriteAction
    data object OnBackClick : FavoriteAction
}
