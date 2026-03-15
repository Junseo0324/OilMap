package com.devhjs.oilmap.domain.usecase

import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.model.SortType
import com.devhjs.oilmap.domain.model.UserPreferences
import com.devhjs.oilmap.domain.repository.UserPreferenceRepository
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetUserPreferenceUseCaseTest {

    private lateinit var getUserPreferenceUseCase: GetUserPreferenceUseCase
    private val repository: UserPreferenceRepository = mockk()

    @Before
    fun setUp() {
        getUserPreferenceUseCase = GetUserPreferenceUseCase(repository)
    }

    @Test
    fun `사용자 설정 Flow를 반환한다`() = runTest {
        // Given
        val preferences = UserPreferences(
            favoriteOilType = OilType.GASOLINE,
            defaultSortType = SortType.DISTANCE,
            searchRadius = 3000
        )
        every { repository.userPreferencesFlow } returns flowOf(preferences)

        // When
        val resultFlow = getUserPreferenceUseCase()

        // Then
        val result = resultFlow.first()
        assertEquals(OilType.GASOLINE, result.favoriteOilType)
        assertEquals(3000, result.searchRadius)
        
        coVerify(exactly = 1) { repository.userPreferencesFlow }
    }
}
