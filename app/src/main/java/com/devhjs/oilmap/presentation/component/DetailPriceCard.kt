package com.devhjs.oilmap.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.oilmap.domain.model.StationDetail
import com.devhjs.oilmap.presentation.designsystem.AppColors
import com.devhjs.oilmap.presentation.designsystem.AppTextStyles

@Composable
fun DetailPriceCard(detail: StationDetail) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(14.dp))
            .background(Color.White, RoundedCornerShape(14.dp))
            .border(1.dp, AppColors.Border, RoundedCornerShape(14.dp))
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "💰 유종별 가격",
            style = AppTextStyles.headlineMedium.copy(fontSize = 18.sp, fontWeight = FontWeight.Bold),
            color = AppColors.Gray900
        )

        // 휘발유
        if (detail.gasolinePrice > 0) {
            DetailPriceRow(
                label = "휘발유",
                subLabel = "Gasoline",
                initial = "휘",
                price = detail.gasolinePrice,
                color = AppColors.Gasoline
            )
        }
        // 고급휘발유
        if (detail.premiumGasolinePrice > 0) {
            DetailPriceRow(
                label = "고급휘발유",
                subLabel = "Premium Gasoline",
                initial = "고",
                price = detail.premiumGasolinePrice,
                color = AppColors.PremiumGasoline
            )
        }
        // 경유
        if (detail.dieselPrice > 0) {
            DetailPriceRow(
                label = "경유",
                subLabel = "Diesel",
                initial = "경",
                price = detail.dieselPrice,
                color = AppColors.Diesel
            )
        }
        // LPG
        if (detail.lpgPrice > 0) {
            DetailPriceRow(
                label = "LPG",
                subLabel = "Liquefied Petroleum Gas",
                initial = "L",
                price = detail.lpgPrice,
                color = AppColors.Lpg
            )
        }
    }
}
