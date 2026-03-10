package com.devhjs.oilmap.domain.model

data class UserPreferences(
    val favoriteOilType: OilType,
    val defaultSortType: SortType,
    val searchRadius: Int
)
