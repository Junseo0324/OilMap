package com.devhjs.oilmap.domain.usecase

import com.devhjs.oilmap.domain.model.UserPreferences
import com.devhjs.oilmap.domain.repository.UserPreferenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserPreferenceUseCase @Inject constructor(
    private val repository: UserPreferenceRepository
) {
    operator fun invoke(): Flow<UserPreferences> {
        return repository.userPreferencesFlow
    }
}
