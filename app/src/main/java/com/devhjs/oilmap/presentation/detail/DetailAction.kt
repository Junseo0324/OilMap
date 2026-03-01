package com.devhjs.oilmap.presentation.detail

/**
 * 상세 화면 사용자 액션
 */
sealed interface DetailAction {
    data object OnBackClick : DetailAction
    data object OnCallClick : DetailAction
    data object OnNavigateClick : DetailAction
    data object OnRefreshClick : DetailAction
    data object OnFavoriteToggle : DetailAction
}
