package com.devhjs.oilmap.domain.usecase

import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.model.Station
import com.devhjs.oilmap.domain.repository.StationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteStationsUseCase @Inject constructor(
    private val repository: StationRepository
) {
    /**
     * 즐겨찾기한 주유소 목록 조회
     * @param oilType 선택된 유종 (가격 매핑용)
     * @return 주유소 목록 Flow
     */
    operator fun invoke(oilType: OilType = OilType.GASOLINE): Flow<List<Station>> {
        return repository.getFavoriteStations(oilType)
    }
}
