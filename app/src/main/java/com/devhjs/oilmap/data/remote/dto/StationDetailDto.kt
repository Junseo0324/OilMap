package com.devhjs.oilmap.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StationDetailDto(
    @SerialName("UNI_ID") val stationId: String,
    @SerialName("OS_NM") val name: String = "",
    @SerialName("POLL_DIV_CD") val brandCode: String = "",
    @SerialName("NEW_ADR") val newAddress: String? = null,
    @SerialName("VAN_ADR") val oldAddress: String? = null,
    @SerialName("TEL") val tel: String? = null,
    @SerialName("CAR_WASH_YN") val hasCarWash: String? = null,
    @SerialName("MAINT_YN") val hasMaintenance: String? = null,
    @SerialName("CVS_YN") val hasConvenienceStore: String? = null,
    @SerialName("KPETRO_YN") val isQualityCertified: String? = null,
    @SerialName("LPG_YN") val hasLpg: String? = null
)

