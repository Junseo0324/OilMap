package com.devhjs.oilmap.data.remote.api

import com.devhjs.oilmap.BuildConfig
import com.devhjs.oilmap.data.remote.dto.OpinetResponse
import com.devhjs.oilmap.data.remote.dto.StationDetailDto
import com.devhjs.oilmap.data.remote.dto.StationDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OpinetService {
    @GET("aroundAll.do")
    suspend fun getStationsAround(
        @Query("code") code: String = BuildConfig.OPINET_API_KEY,
        @Query("out") out: String = "json",
        @Query("x") x: Double,
        @Query("y") y: Double,
        @Query("radius") radius: Int = 5000,
        @Query("prodcd") prodcd: String,
        @Query("sort") sort: Int = 1
    ): OpinetResponse<StationDto>

    @GET("detailById.do")
    suspend fun getStationDetail(
        @Query("code") code: String = BuildConfig.OPINET_API_KEY,
        @Query("out") out: String = "json",
        @Query("id") stationId: String
    ): OpinetResponse<StationDetailDto>

    @GET("lowTop10.do")
    suspend fun getLowPriceTop(
        @Query("code") code: String = BuildConfig.OPINET_API_KEY,
        @Query("out") out: String = "json",
        @Query("prodcd") prodcd: String,
        @Query("area") area: String? = null,
        @Query("cnt") cnt: Int = 10
    ): OpinetResponse<StationDto>
}
