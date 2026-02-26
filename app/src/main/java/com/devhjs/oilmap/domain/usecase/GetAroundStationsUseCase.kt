package com.devhjs.oilmap.domain.usecase

import com.devhjs.oilmap.core.util.DataError
import com.devhjs.oilmap.core.util.Result
import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.model.SortType
import com.devhjs.oilmap.domain.model.Station
import com.devhjs.oilmap.domain.repository.StationRepository
import javax.inject.Inject

class GetAroundStationsUseCase @Inject constructor(
    private val repository: StationRepository
) {
    /**
     * 현재 위치 기반 주변 주유소 목록 조회
     * @param x KATEC X 좌표
     * @param y KATEC Y 좌표
     * @param radius 조회 반경 (m)
     * @param oilType 유종 (휘발유, 경유 등)
     * @param sortType 정렬 기준 (가격순, 거리순)
     */
    suspend operator fun invoke(
        x: Double,
        y: Double,
        radius: Int = 5000,
        oilType: OilType = OilType.GASOLINE,
        sortType: SortType = SortType.PRICE
    ): Result<List<Station>, DataError> {
        return try {
            val stations = repository.getAroundStations(x, y, radius, oilType, sortType)
            Result.Success(stations)
        } catch (e: Exception) {
            // TODO: 예외 타입에 따른 세부 에러 처리 (Data Layer 구현 시 보완)
            Result.Error(DataError.Network.UNKNOWN)
        }
    }
}
