package com.devhjs.oilmap.domain.usecase

import com.devhjs.oilmap.core.util.DataError
import com.devhjs.oilmap.core.util.Result
import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.model.SortType
import com.devhjs.oilmap.domain.model.Station
import com.devhjs.oilmap.domain.repository.StationRepository
import com.devhjs.oilmap.core.util.LocationUtil
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
        sortType: SortType = SortType.PRICE
    ): Result<List<Station>, DataError> {
        return try {
            // 1. WGS84 -> KATEC 좌표 변환 (API 스펙 및 거리 계산용)
            // LocationUtil은 (lat, lng) 순서를 받습니다.
            val (katecX, katecY) = LocationUtil.wgs84ToKatec(lat, lng)

            // 2. Repository 호출 (데이터 소스 추상화 풀링)
            val rawStations = repository.getAroundStations(katecX, katecY, radius, oilType, sortType)

            // 3. 비즈니스 규칙 적용: 정확한 거리 계산 및 정렬 (캐시 데이터의 경우 거리가 Null일 수 있으므로)
            val processedStations = rawStations.map { station ->
                // API에서 내려준 거리가 없거나, 캐시 데이터인 경우 현재 요청된 기준점으로 재계산
                val calculatedDistance = station.distance ?: station.let {
                    if (it.x != null && it.y != null) {
                        sqrt((it.x - katecX).let { dx -> dx * dx } + (it.y - katecY).let { dy -> dy * dy })
                    } else {
                        0.0
                    }
                }
                station.copy(distance = calculatedDistance)
            }.run {
                // 선택된 정렬 기준에 따라 최종 정렬
                if (sortType == SortType.PRICE) sortedBy { it.price }
                else sortedBy { it.distance }
            }

            Result.Success(processedStations)
        } catch (e: Exception) {
            android.util.Log.e("GetAroundStationsUseCase", "Error mapping coordinates or fetching API", e)
            e.printStackTrace()
            Result.Error(DataError.Network.UNKNOWN)
        }
    }
}
