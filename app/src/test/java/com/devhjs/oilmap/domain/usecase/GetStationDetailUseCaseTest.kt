package com.devhjs.oilmap.domain.usecase

import com.devhjs.oilmap.core.util.Result
import com.devhjs.oilmap.domain.model.StationDetail
import com.devhjs.oilmap.domain.repository.StationRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetStationDetailUseCaseTest {

    private lateinit var getStationDetailUseCase: GetStationDetailUseCase
    private val repository: StationRepository = mockk()

    @Before
    fun setUp() {
        getStationDetailUseCase = GetStationDetailUseCase(repository)
    }

    @Test
    fun `주유소_상세_정보를_가져온다`() = runTest {
        // Given
        val stationId = "1"
        val stationDetail = StationDetail(
            id = "1", name = "A주유소", brandCode = "S001", address = "Address", tel = "02-123-4567",
            distance = 100.0, gasolinePrice = 1500, premiumGasolinePrice = 0, dieselPrice = 1400, lpgPrice = 0,
            hasCarWash = true, hasMaintenance = false, hasConvenienceStore = true, isQualityCertified = false, isFavorite = false
        )
        coEvery { repository.getStationDetail(stationId, 1.0, 2.0) } returns stationDetail

        // When
        val result = getStationDetailUseCase(stationId, 1.0, 2.0)

        // Then
        assertTrue(result is Result.Success)
        val data = (result as Result.Success).data
        assertEquals("A주유소", data.name)
        assertEquals(1500, data.gasolinePrice)
        
        coVerify(exactly = 1) { repository.getStationDetail(stationId, 1.0, 2.0) }
    }

    @Test
    fun `오류 발생 시 Result Error를 반환한다`() = runTest {
        // Given
        coEvery { repository.getStationDetail(any(), any(), any()) } throws Exception("Not found")

        // When
        val result = getStationDetailUseCase("invalid")

        // Then
        assertTrue(result is Result.Error)
        coVerify(exactly = 1) { repository.getStationDetail("invalid", null, null) }
    }
}
