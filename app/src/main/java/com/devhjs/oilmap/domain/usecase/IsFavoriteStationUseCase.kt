package com.devhjs.oilmap.domain.usecase

import com.devhjs.oilmap.core.util.DataError
import com.devhjs.oilmap.core.util.Result
import com.devhjs.oilmap.domain.repository.StationRepository
import javax.inject.Inject

class IsFavoriteStationUseCase @Inject constructor(
    private val repository: StationRepository
) {
    /**
     * 특정 주유소의 즐겨찾기 등록 여부 확인
     * @param stationId 주유소 아이디
     * @return 즐겨찾기 등록 여부 (Boolean)
     */
    suspend operator fun invoke(stationId: String): Result<Boolean, DataError> {
        return try {
            val isFavorite = repository.isFavorite(stationId)
            Result.Success(isFavorite)
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }
}
