package com.devhjs.oilmap.data.repository

import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.model.SortType
import com.devhjs.oilmap.domain.model.Station
import com.devhjs.oilmap.domain.model.StationDetail
import com.devhjs.oilmap.domain.repository.StationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class MockStationRepositoryImpl @Inject constructor() : StationRepository {

    override suspend fun getAroundStations(
        katecX: Double,
        katecY: Double,
        radius: Int,
        oilType: OilType,
        sortType: SortType
    ): List<Station> {
        return buildList {
            repeat(10) { index ->
                add(
                    Station(
                        id = "mock_station_$index",
                        name = "가짜 주유소 $index",
                        brandCode = arrayOf("SKE", "GSC", "HDO", "RTE").random(),
                        price = 1500 + (index * 10),
                        distance = null,
                        x = katecX + (index * 100),
                        y = katecY + (index * 100),
                        address = "가짜시 가짜구 가짜동 $index",
                        tel = "010-0000-000$index",
                        hasCarWash = index % 2 == 0,
                        hasMaintenance = index % 3 == 0,
                        hasConvenienceStore = index % 4 == 0,
                        isQualityCertified = index % 5 == 0,
                        isFavorite = index % 3 == 0
                    )
                )
            }
        }
    }

    override suspend fun getStationDetail(
        stationId: String,
        userKatecX: Double?,
        userKatecY: Double?
    ): StationDetail {
        return StationDetail(
            id = stationId,
            name = "가짜 상세 주유소",
            brandCode = "SKE",
            address = "서울시 강남구 도곡동 333",
            tel = "02-1234-5678",
            distance = 1200.0,
            gasolinePrice = 1538,
            premiumGasolinePrice = 1590,
            dieselPrice = 1371,
            lpgPrice = 920,
            hasCarWash = true,
            hasMaintenance = false,
            hasConvenienceStore = true,
            isQualityCertified = true,
            isFavorite = false
        )
    }

    override suspend fun getLowPriceStations(oilType: OilType, area: String?): List<Station> {
        return getAroundStations(0.0, 0.0, 5000, oilType, SortType.PRICE).sortedBy { it.price }
    }

    override fun getFavoriteStations(oilType: OilType): Flow<List<Station>> {
        val mockFavorites = listOf(
            Station(id = "mock_fav_1", name = "단골 주유소 1", brandCode = "SKE", price = 1500, isFavorite = true),
            Station(id = "mock_fav_2", name = "단골 주유소 2", brandCode = "GSC", price = 1520, isFavorite = true)
        )
        return flowOf(mockFavorites)
    }

    override suspend fun toggleFavorite(station: Station) {
        println("MockStationRepositoryImpl: toggleFavorite -> ${station.name}")
    }

    override suspend fun isFavorite(stationId: String): Boolean {
        return stationId.contains("fav")
    }
}
