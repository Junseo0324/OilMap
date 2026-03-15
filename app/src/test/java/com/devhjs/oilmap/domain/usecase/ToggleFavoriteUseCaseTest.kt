package com.devhjs.oilmap.domain.usecase

import com.devhjs.oilmap.domain.model.Station
import com.devhjs.oilmap.domain.repository.StationRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ToggleFavoriteUseCaseTest {

    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase
    private val repository: StationRepository = mockk()

    @Before
    fun setUp() {
        toggleFavoriteUseCase = ToggleFavoriteUseCase(repository)
    }

    @Test
    fun `즐겨찾기 상태를 토글한다`() = runTest {
        // Given
        val station = Station(id = "1", name = "A주유소", brandCode = "S001", price = 1500)
        coEvery { repository.toggleFavorite(station) } just runs

        // When
        toggleFavoriteUseCase(station)

        // Then
        coVerify(exactly = 1) { repository.toggleFavorite(station) }
    }
}
