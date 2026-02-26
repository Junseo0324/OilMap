package com.devhjs.oilmap.core.util

/**
 * 도메인 계층에서 발생하는 모든 에러의 기본 인터페이스
 */
sealed interface Error

/**
 * 데이터 처리(네트워크, 로컬 DB 등) 중 발생하는 에러 정의
 */
sealed interface DataError : Error {
    enum class Network : DataError {
        SERVICE_UNAVAILABLE,
        CLIENT_ERROR,
        SERVER_ERROR,
        NO_INTERNET,
        UNKNOWN
    }

    enum class Local : DataError {
        DISK_FULL,
        UNKNOWN
    }
}
