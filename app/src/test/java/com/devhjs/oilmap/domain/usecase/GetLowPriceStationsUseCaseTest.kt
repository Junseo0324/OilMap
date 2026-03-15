package com.devhjs.oilmap.domain.usecase

import com.devhjs.oilmap.core.util.Result
import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.model.Station
import com.devhjs.oilmap.domain.repository.StationRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetLowPriceStationsUseCaseTest {

    private lateinit var getLowPriceStationsUseCase: GetLowPriceStationsUseCase
    private val repository: StationRepository = mockk()

    @Before
    fun setUp() {
        getLowPriceStationsUseCase = GetLowPriceStationsUseCase(repository)
    }

    @Test
    fun `최저가_주유소_목록을_가져온다`() = runTest {
        // Given
        val oilType = OilType.GASOLINE
        val stations = listOf(
            Station(id = "1", name = "A주유소", brandCode = "S001", price = 1300),
            Station(id = "2", name = "B주유소", brandCode = "S002", price = 1350)
        )
        coEvery { repository.getLowPriceStations(oilType, null) } returns stations

        // When
        val result = getLowPriceStationsUseCase(oilType)

        // Then
        assertTrue(result is Result.Success)
        val data = (result as Result.Success).data
        assertEquals(2, data.size)
        assertEquals(1300, data[0].price)
        
        coVerify(exactly = 1) { repository.getLowPriceStations(oilType, null) }
    }

    @Test
    fun `오류_발생_시_Result_Error를_반환한다`() = runTest {
        // Given
        coEvery { repository.getLowPriceStations(any(), any()) } throws Exception("Network error")

        // When
        val result = getLowPriceStationsUseCase()

        // Then
        assertTrue(result is Result.Error)
        coVerify(exactly = 1) { repository.getLowPriceStations(any(), any()) }
    }
}
