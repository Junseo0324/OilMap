package com.devhjs.oilmap.domain.usecase

import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.model.Station
import com.devhjs.oilmap.domain.repository.StationRepository
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetFavoriteStationsUseCaseTest {

    private lateinit var getFavoriteStationsUseCase: GetFavoriteStationsUseCase
    private val repository: StationRepository = mockk()

    @Before
    fun setUp() {
        getFavoriteStationsUseCase = GetFavoriteStationsUseCase(repository)
    }

    @Test
    fun `즐겨찾기한 주유소 목록 Flow를 반환한다`() = runTest {
        // Given
        val oilType = OilType.GASOLINE
        val stations = listOf(
            Station(id = "1", name = "A주유소", brandCode = "S001", price = 1500),
            Station(id = "2", name = "B주유소", brandCode = "S002", price = 1400)
        )
        every { repository.getFavoriteStations(oilType) } returns flowOf(stations)

        // When
        val resultFlow = getFavoriteStationsUseCase(oilType)

        // Then
        val resultList = resultFlow.first()
        assertEquals(2, resultList.size)
        assertEquals("A주유소", resultList[0].name)
        assertEquals("B주유소", resultList[1].name)
        
        coVerify(exactly = 1) { repository.getFavoriteStations(oilType) }
    }
}
