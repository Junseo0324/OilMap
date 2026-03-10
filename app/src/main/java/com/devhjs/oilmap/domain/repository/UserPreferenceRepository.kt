package com.devhjs.oilmap.domain.repository

import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.model.SortType
import com.devhjs.oilmap.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferenceRepository {
    val userPreferencesFlow: Flow<UserPreferences>

    suspend fun updateFavoriteOilType(oilType: OilType)
    suspend fun updateDefaultSortType(sortType: SortType)
    suspend fun updateSearchRadius(radius: Int)
}
