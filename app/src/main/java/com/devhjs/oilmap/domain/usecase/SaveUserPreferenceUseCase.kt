package com.devhjs.oilmap.domain.usecase

import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.model.SortType
import com.devhjs.oilmap.domain.repository.UserPreferenceRepository
import javax.inject.Inject

class SaveUserPreferenceUseCase @Inject constructor(
    private val repository: UserPreferenceRepository
) {
    suspend fun updateFavoriteOilType(oilType: OilType) {
        repository.updateFavoriteOilType(oilType)
    }

    suspend fun updateDefaultSortType(sortType: SortType) {
        repository.updateDefaultSortType(sortType)
    }

    suspend fun updateSearchRadius(radius: Int) {
        repository.updateSearchRadius(radius)
    }
}
