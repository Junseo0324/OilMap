package com.devhjs.oilmap.presentation.designsystem

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object AppTextStyles {
    val headlineLarge = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
        color = Color(0xFF111827)
    )

    val headlineMedium = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = Color(0xFF111827)
    )

    // ========== 본문 텍스트 ==========

    val titleLarge = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = Color(0xFF111827)
    )

    val bodyLarge = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = Color(0xFF111827)
    )

    val bodyMedium = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.25.sp,
        color = Color(0xFF111827)
    )

    val bodySmall = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
        color = Color(0xFF4B5563)
    )

    // ========== 라벨 & 버튼 ==========

    val labelMedium = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        color = Color(0xFF6B7280)
    )

    val labelExtraSmall = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp,
        color = Color(0xFF374151)
    )

    // ========== 가격 표시 (핵심) ==========

    val priceDisplay = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
        color = Color(0xFF111827)
    )


    val priceDetailDisplay = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
        color = Color(0xFF111827)
    )


    val captionMedium = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp,
        color = Color(0xFF6B7280)
    )

    val noDataText = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = Color(0xFF9CA3AF)
    )

    // ========== 리스트 카운트 ==========

    val listCountNormal = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
        color = Color(0xFF4B5563)
    )

    val listCountBold = TextStyle(
        fontFamily = AppFonts.pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
        color = Color(0xFF111827)
    )

}