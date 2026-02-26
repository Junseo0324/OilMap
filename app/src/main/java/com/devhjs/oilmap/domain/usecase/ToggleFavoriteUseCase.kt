package com.devhjs.oilmap.domain.usecase

import com.devhjs.oilmap.domain.model.Station
import com.devhjs.oilmap.domain.repository.StationRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: StationRepository
) {
    /**
     * 주유소 즐겨찾기 상태 토글
     * @param station 즐겨찾기에 추가하거나 제거할 주유소 객체
     */
    suspend operator fun invoke(station: Station) {
        repository.toggleFavorite(station)
    }
}
