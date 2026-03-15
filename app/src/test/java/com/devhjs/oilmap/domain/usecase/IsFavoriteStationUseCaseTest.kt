package com.devhjs.oilmap.domain.usecase

import com.devhjs.oilmap.core.util.Result
import com.devhjs.oilmap.domain.repository.StationRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class IsFavoriteStationUseCaseTest {

    private lateinit var isFavoriteStationUseCase: IsFavoriteStationUseCase
    private val repository: StationRepository = mockk()

    @Before
    fun setUp() {
        isFavoriteStationUseCase = IsFavoriteStationUseCase(repository)
    }

    @Test
    fun `즐겨찾기 여부를 확인하여 반환한다`() = runTest {
        // Given
        coEvery { repository.isFavorite("1") } returns true

        // When
        val result = isFavoriteStationUseCase("1")

        // Then
        assertTrue(result is Result.Success)
        assertEquals(true, (result as Result.Success).data)
        
        coVerify(exactly = 1) { repository.isFavorite("1") }
    }

    @Test
    fun `오류 발생 시 Result Error를 반환한다`() = runTest {
        // Given
        coEvery { repository.isFavorite(any()) } throws Exception("DB error")

        // When
        val result = isFavoriteStationUseCase("1")

        // Then
        assertTrue(result is Result.Error)
        coVerify(exactly = 1) { repository.isFavorite("1") }
    }
}
