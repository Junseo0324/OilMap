package com.devhjs.oilmap.domain.usecase

import com.devhjs.oilmap.core.util.LocationUtil
import com.devhjs.oilmap.core.util.Result
import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.model.SortType
import com.devhjs.oilmap.domain.model.Station
import com.devhjs.oilmap.domain.repository.StationRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetAroundStationsUseCaseTest {

    private lateinit var getAroundStationsUseCase: GetAroundStationsUseCase
    private val repository: StationRepository = mockk()

    @Before
    fun setUp() {
        getAroundStationsUseCase = GetAroundStationsUseCase(repository)
        mockkObject(LocationUtil)
    }

    @After
    fun tearDown() {
        unmockkObject(LocationUtil)
    }

    @Test
    fun `주변_주유소_목록을_가져오고_거리순으로_정렬한다`() = runTest {
        // Given
        val lat = 37.0
        val lng = 127.0
        val katecX = 200000.0
        val katecY = 400000.0
        
        every { LocationUtil.wgs84ToKatec(lat, lng) } returns Pair(katecX, katecY)
        
        val stations = listOf(
            Station(id = "1", name = "A주유소", brandCode = "S001", price = 1500, x = 200100.0, y = 400100.0), // 거리 약 141
            Station(id = "2", name = "B주유소", brandCode = "S002", price = 1400, x = 200050.0, y = 400050.0)  // 거리 약 70
        )
        
        coEvery { 
            repository.getAroundStations(katecX, katecY, 5000, OilType.GASOLINE, SortType.DISTANCE) 
        } returns stations

        // When
        val result = getAroundStationsUseCase(lat, lng, 5000, OilType.GASOLINE, SortType.DISTANCE)

        // Then
        assertTrue("결과는 Success여야 합니다", result is Result.Success)
        val data = (result as Result.Success).data
        assertEquals("주유소 개수가 일치하지 않습니다", 2, data.size)
        assertEquals("거리순 정렬이 올바르지 않습니다 (B주유소)", "2", data[0].id)
        assertEquals("거리순 정렬이 올바르지 않습니다 (A주유소)", "1", data[1].id)
        
        coVerify(exactly = 1) { 
            repository.getAroundStations(katecX, katecY, 5000, OilType.GASOLINE, SortType.DISTANCE) 
        }
    }

    @Test
    fun `주변_주유소_목록을_가격순으로_정렬한다`() = runTest {
        // Given
        val lat = 37.0
        val lng = 127.0
        val katecX = 200000.0
        val katecY = 400000.0
        
        every { LocationUtil.wgs84ToKatec(lat, lng) } returns Pair(katecX, katecY)
        
        val stations = listOf(
            Station(id = "1", name = "A주유소", brandCode = "S001", price = 1500, x = 200050.0, y = 400050.0),
            Station(id = "2", name = "B주유소", brandCode = "S002", price = 1400, x = 200100.0, y = 400100.0)
        )
        
        coEvery { 
            repository.getAroundStations(katecX, katecY, 5000, OilType.GASOLINE, SortType.PRICE) 
        } returns stations

        // When
        val result = getAroundStationsUseCase(lat, lng, 5000, OilType.GASOLINE, SortType.PRICE)

        // Then
        assertTrue(result is Result.Success)
        val data = (result as Result.Success).data
        assertEquals(2, data.size)
        assertEquals("가격순 정렬이 올바르지 않습니다", "2", data[0].id) 
        assertEquals("1", data[1].id)
        
        coVerify(exactly = 1) { 
            repository.getAroundStations(katecX, katecY, 5000, OilType.GASOLINE, SortType.PRICE) 
        }
    }

    @Test
    fun `가격이_0인_주유소는_목록에서_제외한다`() = runTest {
        // Given
        val lat = 37.0
        val lng = 127.0
        val katecX = 200000.0
        val katecY = 400000.0
        
        every { LocationUtil.wgs84ToKatec(lat, lng) } returns Pair(katecX, katecY)
        
        val stations = listOf(
            Station(id = "1", name = "A주유소", brandCode = "S001", price = 1500, x = 200050.0, y = 400050.0),
            Station(id = "2", name = "B주유소", brandCode = "S002", price = 0, x = 200100.0, y = 400100.0)
        )
        
        coEvery { 
            repository.getAroundStations(katecX, katecY, 5000, OilType.GASOLINE, SortType.DISTANCE) 
        } returns stations

        // When
        val result = getAroundStationsUseCase(lat, lng, 5000, OilType.GASOLINE, SortType.DISTANCE)

        // Then
        assertTrue(result is Result.Success)
        val data = (result as Result.Success).data
        assertEquals("가격이 0인 주유소가 필터링되지 않았습니다", 1, data.size)
        assertEquals("1", data[0].id)
    }
}
