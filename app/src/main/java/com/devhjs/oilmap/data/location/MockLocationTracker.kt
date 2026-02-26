package com.devhjs.oilmap.data.location

import android.location.Location
import com.devhjs.oilmap.domain.location.LocationTracker
import javax.inject.Inject

class MockLocationTracker @Inject constructor() : LocationTracker {

    /**
     * 개발 환경 및 테스트용으로 서울 강남역 인근의 고정된 가짜 위경도를 반환합니다.
     */
    override suspend fun getCurrentLocation(): Location? {
        // android.location.Location 객체를 바로 생성하여 반환합니다.
        val mockLocation = Location("MockProvider").apply {
            latitude = 37.498095 // 강남역 위도
            longitude = 127.027610 // 강남역 경도
            time = System.currentTimeMillis()
            accuracy = 1.0f
        }
        return mockLocation
    }

    override fun getLocationUpdates(intervalMillis: Long): kotlinx.coroutines.flow.Flow<Location?> {
        return kotlinx.coroutines.flow.flow {
            while (true) {
                emit(getCurrentLocation())
                kotlinx.coroutines.delay(intervalMillis)
            }
        }
    }
}
