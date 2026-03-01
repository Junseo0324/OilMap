package com.devhjs.oilmap.domain.usecase

import com.devhjs.oilmap.core.util.DataError
import com.devhjs.oilmap.core.util.Result
import com.devhjs.oilmap.domain.model.StationDetail
import com.devhjs.oilmap.domain.repository.StationRepository
import javax.inject.Inject

class GetStationDetailUseCase @Inject constructor(
    private val repository: StationRepository
) {
    /**
     * 특정 주유소의 상세 정보 조회
     * @param stationId 주유소 고유 ID (UNI_ID)
     */
    suspend operator fun invoke(stationId: String): Result<StationDetail, DataError> {
        return try {
            val stationDetail = repository.getStationDetail(stationId)
            Result.Success(stationDetail)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(DataError.Network.UNKNOWN)
        }
    }
}
