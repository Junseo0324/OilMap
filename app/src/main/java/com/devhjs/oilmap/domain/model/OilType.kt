package com.devhjs.oilmap.domain.model

enum class OilType(val code: String, val displayName: String) {
    GASOLINE("B027", "휘발유"),
    DIESEL("D047", "경유"),
    PREMIUM_GASOLINE("B034", "고급휘발유"),
    KEROSENE("C004", "등유"),
    LPG("K015", "LPG")
}
