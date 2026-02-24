package com.devhjs.oilmap.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StationDto(
    @SerialName("UNI_ID") val stationId: String,
    @SerialName("OS_NM") val name: String,
    @SerialName("POLL_DIV_CD") val brandCode: String,
    @SerialName("PRICE") val price: Int,
    @SerialName("DISTANCE") val distance: Double? = null,
    @SerialName("GIS_X_COOR") val katecX: Double? = null,
    @SerialName("GIS_Y_COOR") val katecY: Double? = null
)
