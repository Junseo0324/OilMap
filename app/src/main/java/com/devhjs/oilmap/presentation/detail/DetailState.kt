package com.devhjs.oilmap.presentation.detail

import com.devhjs.oilmap.domain.model.StationDetail

/**
 * 상세 화면 UI 상태
 */
data class DetailState(
    val isLoading: Boolean = true,
    val stationDetail: StationDetail? = null,
    val error: String? = null
)
