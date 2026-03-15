package com.devhjs.oilmap.domain.usecase

import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.model.SortType
import com.devhjs.oilmap.domain.repository.UserPreferenceRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SaveUserPreferenceUseCaseTest {

    private lateinit var saveUserPreferenceUseCase: SaveUserPreferenceUseCase
    private val repository: UserPreferenceRepository = mockk()

    @Before
    fun setUp() {
        saveUserPreferenceUseCase = SaveUserPreferenceUseCase(repository)
    }

    @Test
    fun `유종_설정을_저장한다`() = runTest {
        // Given
        val oilType = OilType.PREMIUM_GASOLINE
        coEvery { repository.updateFavoriteOilType(oilType) } just runs

        // When
        saveUserPreferenceUseCase.updateFavoriteOilType(oilType)

        // Then
        coVerify(exactly = 1) { repository.updateFavoriteOilType(oilType) }
    }

    @Test
    fun `정렬_기준을_저장한다`() = runTest {
        // Given
        val sortType = SortType.PRICE
        coEvery { repository.updateDefaultSortType(sortType) } just runs

        // When
        saveUserPreferenceUseCase.updateDefaultSortType(sortType)

        // Then
        coVerify(exactly = 1) { repository.updateDefaultSortType(sortType) }
    }

    @Test
    fun `검색_반경을_저장한다`() = runTest {
        // Given
        val radius = 5000
        coEvery { repository.updateSearchRadius(radius) } just runs

        // When
        saveUserPreferenceUseCase.updateSearchRadius(radius)

        // Then
        coVerify(exactly = 1) { repository.updateSearchRadius(radius) }
    }
}
