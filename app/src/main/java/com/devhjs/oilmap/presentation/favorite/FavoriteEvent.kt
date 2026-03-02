package com.devhjs.oilmap.presentation.favorite

// 즐겨찾기 화면에서 ViewModel이 UI에 전달하는 일회성 이벤트
sealed interface FavoriteEvent {
    data class NavigateToStationDetail(val stationId: String) : FavoriteEvent
    data object NavigateBack : FavoriteEvent
}
