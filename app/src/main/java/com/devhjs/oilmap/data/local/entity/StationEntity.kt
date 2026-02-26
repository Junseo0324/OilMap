package com.devhjs.oilmap.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stations")
data class StationEntity(
    @PrimaryKey 
    val stationId: String,
    val name: String,
    val brandCode: String,
    val x: Double,
    val y: Double,
    val address: String,
    val tel: String,
    val hasCarWash: Boolean,
    val hasMaintenance: Boolean,
    val hasConvenienceStore: Boolean,
    val isQualityCertified: Boolean,
    
    val gasolinePrice: Int,
    val dieselPrice: Int,
    val premiumGasolinePrice: Int,
    val kerosenePrice: Int,
    val lpgPrice: Int,

    val isFavorite: Boolean,
    val lastUpdated: Long
)
