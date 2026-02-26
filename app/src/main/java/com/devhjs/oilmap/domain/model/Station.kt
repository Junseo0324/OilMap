package com.devhjs.oilmap.domain.model

data class Station(
    val id: String,
    val name: String,
    val brandCode: String,
    val price: Int,
    val distance: Double? = null,
    val x: Double? = null,
    val y: Double? = null,
    val address: String? = null,
    val tel: String? = null,
    val hasCarWash: Boolean = false,
    val hasMaintenance: Boolean = false,
    val hasConvenienceStore: Boolean = false,
    val isQualityCertified: Boolean = false,
    val isFavorite: Boolean = false
)
