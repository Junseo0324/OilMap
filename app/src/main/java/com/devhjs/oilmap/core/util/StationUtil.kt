package com.devhjs.oilmap.core.util

/**
 * 오피넷 브랜드 코드를 한글 브랜드명으로 변환합니다.
 */
fun getBrandName(code: String): String = when (code) {
    "SKE" -> "SK에너지"
    "GSC" -> "GS칼텍스"
    "HDO" -> "HD현대오일뱅크"
    "SOL" -> "S-OIL"
    "RTO" -> "자영알뜰"
    "RTX" -> "고속도로알뜰"
    "NHO" -> "농협알뜰"
    "ETC" -> "기타"
    "E1G" -> "E1"
    "SKG" -> "SK가스"
    else -> code
}
