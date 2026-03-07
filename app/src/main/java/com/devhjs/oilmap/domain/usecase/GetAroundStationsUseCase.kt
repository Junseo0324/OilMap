package com.devhjs.oilmap.domain.usecase

import com.devhjs.oilmap.core.util.DataError
import com.devhjs.oilmap.core.util.LocationUtil
import com.devhjs.oilmap.core.util.Result
import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.model.SortType
import com.devhjs.oilmap.domain.model.Station
import com.devhjs.oilmap.domain.repository.StationRepository
import javax.inject.Inject
import kotlin.math.sqrt

class GetAroundStationsUseCase @Inject constructor(
    private val repository: StationRepository
) {
    /**
     * 현재 위치 기반 주변 주유소 목록 조회 (비즈니스 로직 캡슐화)
     * @param lat WGS84 위도 (Latitude)
     * @param lng WGS84 경도 (Longitude)
     * @param radius 조회 반경 (m)
     * @param oilType 유종 (휘발유, 경유 등)
     * @param sortType 정렬 기준 (가격순, 거리순)
     */
    suspend operator fun invoke(
        lat: Double,
        lng: Double,
        radius: Int = 5000,
        oilType: OilType = OilType.GASOLINE,
        sortType: SortType = SortType.DISTANCE
    ): Result<List<Station>, DataError> {
        return try {
            val (katecX, katecY) = LocationUtil.wgs84ToKatec(lat, lng)

            val rawStations = repository.getAroundStations(katecX, katecY, radius, oilType, sortType)

            val processedStations = rawStations
                .filter { it.price > 0 }
                .map { station ->
                val calculatedDistance = station.distance ?: station.let {
                    if (it.x != null && it.y != null) {
                        sqrt((it.x - katecX).let { dx -> dx * dx } + (it.y - katecY).let { dy -> dy * dy })
                    } else {
                        0.0
                    }
                }
                station.copy(distance = calculatedDistance)
            }.run {
                if (sortType == SortType.PRICE) sortedBy { it.price }
                else sortedBy { it.distance }
            }

            Result.Success(processedStations)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(DataError.Network.UNKNOWN)
        }
    }
}
