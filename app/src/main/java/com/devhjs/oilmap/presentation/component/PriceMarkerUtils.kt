package com.devhjs.oilmap.presentation.component

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Typeface
import androidx.core.graphics.createBitmap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import java.text.NumberFormat
import java.util.Locale

/**
 * 가격이 표시된 커스텀 마커 BitmapDescriptor를 생성하는 유틸 함수
 */
fun createPriceMarkerBitmap(
    price: Int,
    isLowestPrice: Boolean,
    density: Float = 3f
): BitmapDescriptor {
    val priceText = "${NumberFormat.getNumberInstance(Locale.KOREA).format(price)}원"

    val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 12f * density
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        color = if (isLowestPrice) 0xFFFFFFFF.toInt() else 0xFF364153.toInt()
        textAlign = Paint.Align.CENTER
    }

    val textBounds = android.graphics.Rect()
    textPaint.getTextBounds(priceText, 0, priceText.length, textBounds)

    val paddingH = (12f * density).toInt()
    val paddingV = (6f * density).toInt()
    val triangleHeight = (6f * density).toInt()
    val cornerRadius = 8f * density

    val bubbleWidth = textBounds.width() + paddingH * 2
    val bubbleHeight = textBounds.height() + paddingV * 2
    val totalHeight = bubbleHeight + triangleHeight

    val bitmap = createBitmap(bubbleWidth, totalHeight)
    val canvas = Canvas(bitmap)

    val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = if (isLowestPrice) 0xFF00C950.toInt() else 0xFFFFFFFF.toInt()
    }

    val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 1.5f * density
        color = if (isLowestPrice) 0xFF00A63E.toInt() else 0xFFD1D5DB.toInt()
    }

    val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        setShadowLayer(3f * density, 0f, 1.5f * density, 0x40000000)
    }

    val bubbleRect = RectF(
        borderPaint.strokeWidth,
        borderPaint.strokeWidth,
        (bubbleWidth - borderPaint.strokeWidth),
        (bubbleHeight.toFloat() - borderPaint.strokeWidth)
    )

    canvas.drawRoundRect(bubbleRect, cornerRadius, cornerRadius, shadowPaint)

    canvas.drawRoundRect(bubbleRect, cornerRadius, cornerRadius, bgPaint)

    canvas.drawRoundRect(bubbleRect, cornerRadius, cornerRadius, borderPaint)

    val trianglePath = Path().apply {
        val centerX = bubbleWidth / 2f
        val triangleBase = 8f * density
        moveTo(centerX - triangleBase / 2, bubbleHeight.toFloat() - borderPaint.strokeWidth)
        lineTo(centerX, totalHeight.toFloat())
        lineTo(centerX + triangleBase / 2, bubbleHeight.toFloat() - borderPaint.strokeWidth)
        close()
    }
    canvas.drawPath(trianglePath, bgPaint)

    val triangleBorderPath = Path().apply {
        val centerX = bubbleWidth / 2f
        val triangleBase = 8f * density
        moveTo(centerX - triangleBase / 2, bubbleHeight.toFloat() - borderPaint.strokeWidth)
        lineTo(centerX, totalHeight.toFloat())
        lineTo(centerX + triangleBase / 2, bubbleHeight.toFloat() - borderPaint.strokeWidth)
    }
    canvas.drawPath(triangleBorderPath, borderPaint)

    // 가격 텍스트 그리기
    val textX = bubbleWidth / 2f
    val textY = bubbleHeight / 2f + textBounds.height() / 2f
    canvas.drawText(priceText, textX, textY, textPaint)

    return BitmapDescriptorFactory.fromBitmap(bitmap)
}
