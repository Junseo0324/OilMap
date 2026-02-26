package com.devhjs.oilmap.domain.model

enum class SortType(val code: Int, val displayName: String) {
    PRICE(1, "가격순"),
    DISTANCE(2, "거리순")
}
