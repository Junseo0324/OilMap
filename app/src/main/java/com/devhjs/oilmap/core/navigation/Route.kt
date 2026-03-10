package com.devhjs.oilmap.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    // 1. 홈 (바텀 네비게이션 대상 - 리스트)
    @Serializable
    data object Home : Route

    // 2. 지도 (바텀 네비게이션 대상 - 지도)
    @Serializable
    data object Map : Route

    // 3. 즐겨찾기 화면
    @Serializable
    data object Favorite : Route

    // 4. 주유소 상세 화면 (주유소 고유 ID 파라미터 포함)
    @Serializable
    data class Detail(val stationId: String) : Route

    // 5. 설정 화면
    @Serializable
    data object Settings : Route
}
