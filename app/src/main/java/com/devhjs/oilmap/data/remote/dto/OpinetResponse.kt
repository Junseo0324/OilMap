package com.devhjs.oilmap.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpinetResponse<T>(
    @SerialName("RESULT")
    val result: OpinetResult<T>
)

@Serializable
data class OpinetResult<T>(
    @SerialName("OIL")
    val oil: List<T> = emptyList()
)
