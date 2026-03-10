package com.devhjs.oilmap.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.model.SortType
import com.devhjs.oilmap.domain.model.UserPreferences
import com.devhjs.oilmap.domain.repository.UserPreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserPreferenceRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserPreferenceRepository {

    private object PreferencesKeys {
        val FAVORITE_OIL_TYPE = stringPreferencesKey("favorite_oil_type")
        val DEFAULT_SORT_TYPE = intPreferencesKey("default_sort_type")
        val SEARCH_RADIUS = intPreferencesKey("search_radius")
    }

    override val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val oilTypeString = preferences[PreferencesKeys.FAVORITE_OIL_TYPE] ?: OilType.GASOLINE.name
            val oilType = try {
                OilType.valueOf(oilTypeString)
            } catch (e: IllegalArgumentException) {
                OilType.GASOLINE
            }

            val sortTypeCode = preferences[PreferencesKeys.DEFAULT_SORT_TYPE] ?: SortType.DISTANCE.code
            val sortType = SortType.entries.find { it.code == sortTypeCode } ?: SortType.DISTANCE

            val radius = preferences[PreferencesKeys.SEARCH_RADIUS] ?: 5000

            UserPreferences(
                favoriteOilType = oilType,
                defaultSortType = sortType,
                searchRadius = radius
            )
        }

    override suspend fun updateFavoriteOilType(oilType: OilType) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.FAVORITE_OIL_TYPE] = oilType.name
        }
    }

    override suspend fun updateDefaultSortType(sortType: SortType) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DEFAULT_SORT_TYPE] = sortType.code
        }
    }

    override suspend fun updateSearchRadius(radius: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SEARCH_RADIUS] = radius
        }
    }
}
