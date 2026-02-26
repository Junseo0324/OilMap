package com.devhjs.oilmap.domain.location

import android.location.Location

interface LocationTracker {
    /**
     * 디바이스의 현재 위치를 가져옵니다.
     * 권한이 없거나 위치 서비스를 사용할 수 없는 경우 null을 반환합니다.
     */
    suspend fun getCurrentLocation(): Location?
}
