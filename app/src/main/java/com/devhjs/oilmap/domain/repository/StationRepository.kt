package com.devhjs.oilmap.domain.repository

import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.model.SortType
import com.devhjs.oilmap.domain.model.Station
import kotlinx.coroutines.flow.Flow

interface StationRepository {
    // 특정 위치 주변 주유소 조회
    suspend fun getAroundStations(
        katecX: Double,
        katecY: Double,
        radius: Int,
        oilType: OilType,
        sortType: SortType
    ): List<Station>

    // 주유소 상세 정보 조회
    suspend fun getStationDetail(stationId: String): Station

    // 최저가 주유소 Top 10 조회
    suspend fun getLowPriceStations(oilType: OilType, area: String? = null): List<Station>

    // 즐겨찾기 관련
    fun getFavoriteStations(): Flow<List<Station>>
    suspend fun toggleFavorite(station: Station)
    suspend fun isFavorite(stationId: String): Boolean
}
