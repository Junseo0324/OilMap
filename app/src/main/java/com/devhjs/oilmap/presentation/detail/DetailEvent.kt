package com.devhjs.oilmap.presentation.detail

/**
 * 상세 화면 일회성 이벤트
 */
sealed interface DetailEvent {
    data object NavigateBack : DetailEvent
    data class MakeCall(val phoneNumber: String) : DetailEvent
    data class OpenNavigation(val address: String) : DetailEvent
}
