package com.devhjs.oilmap.presentation.designsystem

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object AppTextStyles {
    val headlineLarge = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
        color = Color(0xFF111827) // gray-900
    )

    val headlineMedium = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = Color(0xFF111827) // gray-900
    )

    val headlineSmall = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
        color = Color(0xFF111827) // gray-900
    )

    // ========== 본문 텍스트 ==========

    val titleLarge = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = Color(0xFF111827) // gray-900
    )

    val bodyLarge = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = Color(0xFF111827) // gray-900
    )

    val bodyMedium = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.25.sp,
        color = Color(0xFF111827) // gray-900
    )

    val bodySmall = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
        color = Color(0xFF4B5563) // gray-600
    )

    // ========== 라벨 & 버튼 ==========

    val labelLarge = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.1.sp,
        color = Color.White // 활성 상태
    )

    val labelLargeInactive = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.1.sp,
        color = Color(0xFF374151) // gray-700
    )

    val labelMedium = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        color = Color(0xFF6B7280) // gray-500
    )

    val labelSmall = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        color = Color.White
    )

    val labelExtraSmall = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp,
        color = Color(0xFF374151) // gray-700
    )

    // ========== 가격 표시 (핵심) ==========

    val priceDisplay = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
        color = Color(0xFF111827) // gray-900
    )

    val priceUnit = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = Color(0xFF111827) // gray-900
    )

    val priceDetailDisplay = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
        color = Color(0xFF111827) // gray-900
    )

    val priceDetailUnit = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
        color = Color(0xFF111827) // gray-900
    )

    // ========== 거리 표시 ==========

    val distanceDisplay = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = Color(0xFF4B5563) // gray-600
    )

    // ========== 보조 정보 ==========

    val captionLarge = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
        color = Color(0xFF6B7280) // gray-500
    )

    val captionMedium = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp,
        color = Color(0xFF6B7280) // gray-500
    )

    val noDataText = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = Color(0xFF9CA3AF) // gray-400
    )

    val errorText = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = Color(0xFF4B5563) // gray-600
    )

    // ========== 특수 상태 ==========

    val lowestPriceBadge = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp,
        color = Color.White // on green-500
    )

    val closedBadge = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
        color = Color(0xFFB91C1C) // red-700
    )

    // ========== 리스트 카운트 ==========

    val listCountNormal = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
        color = Color(0xFF4B5563) // gray-600
    )

    val listCountBold = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
        color = Color(0xFF111827) // gray-900
    )

// ========== 색상 참조 ==========
/*
주요 색상:
- gray-900: #111827 (주요 텍스트)
- gray-700: #374151 (비활성 버튼)
- gray-600: #4B5563 (보조 텍스트)
- gray-500: #6B7280 (라벨, 메타 정보)
- gray-400: #9CA3AF (비활성 텍스트)
- blue-500: #3B82F6 (활성 버튼 배경)
- blue-700: #1D4ED8 (링크, 태그)
- green-500: #10B981 (최저가 강조)
- red-700: #B91C1C (경고, 영업종료)
- yellow-500: #EAB308 (즐겨찾기)
- white: #FFFFFF (버튼 텍스트)
 */
}