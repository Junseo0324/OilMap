package com.devhjs.oilmap.domain.model

/**
 * 주유소 상세 정보 도메인 모델
 * 유종별 가격을 모두 포함하여 상세 화면에서 활용합니다.
 */
data class StationDetail(
    val id: String,
    val name: String,
    val brandCode: String,
    val address: String,
    val tel: String,
    val distance: Double?,
    val gasolinePrice: Int,
    val premiumGasolinePrice: Int,
    val dieselPrice: Int,
    val lpgPrice: Int,
    val hasCarWash: Boolean,
    val hasMaintenance: Boolean,
    val hasConvenienceStore: Boolean,
    val isQualityCertified: Boolean,
    val isFavorite: Boolean,
    val lastUpdated: Long? = null
)
