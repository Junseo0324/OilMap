package com.devhjs.oilmap.domain.usecase

import com.devhjs.oilmap.core.util.DataError
import com.devhjs.oilmap.core.util.Result
import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.model.Station
import com.devhjs.oilmap.domain.repository.StationRepository
import javax.inject.Inject

class GetLowPriceStationsUseCase @Inject constructor(
    private val repository: StationRepository
) {
    /**
     * 최저가 주유소 Top 10 조회
     * @param oilType 유종
     * @param area 지역 코드 (null일 경우 전국)
     */
    suspend operator fun invoke(
        oilType: OilType = OilType.GASOLINE,
        area: String? = null
    ): Result<List<Station>, DataError> {
        return try {
            val stations = repository.getLowPriceStations(oilType, area)
            Result.Success(stations)
        } catch (e: Exception) {
            Result.Error(DataError.Network.UNKNOWN)
        }
    }
}
