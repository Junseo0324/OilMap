package com.devhjs.oilmap.core.util

import java.time.LocalTime
import java.time.format.DateTimeFormatter

object TimeUtil {
    /**
     * 현재 시간이 주어진 오픈 시간과 마감 시간 사이에 있는지 확인합니다.
     * API에서 영업시간 데이터를 제공하지 않는 경우 기본값(06:00 ~ 23:59)을 사용합니다.
     *
     * @param openTimeStr 영업 시작 시간 (HH:mm)
     * @param closeTimeStr 영업 종료 시간 (HH:mm)
     * @return 현재 영업 중이면 true, 아니면 false
     */
    fun isOpenNow(openTimeStr: String = "06:00", closeTimeStr: String = "23:59"): Boolean {
        return try {
            val now = LocalTime.now()
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            val openTime = LocalTime.parse(openTimeStr, formatter)
            val closeTime = LocalTime.parse(closeTimeStr, formatter)

            if (openTime.isBefore(closeTime)) {
                // 주간 영업 (예: 06:00 ~ 23:59)
                !now.isBefore(openTime) && !now.isAfter(closeTime)
            } else {
                // 야간/새벽 영업 (예: 18:00 ~ 02:00)
                !now.isBefore(openTime) || !now.isAfter(closeTime)
            }
        } catch (e: Exception) {
            // 시간 파싱에 실패하면 기본적으로 영업 중(true)으로 간주
            true
        }
    }
}
