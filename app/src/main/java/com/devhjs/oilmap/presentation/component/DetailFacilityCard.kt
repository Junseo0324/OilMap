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
fun DetailFacilityCard(detail: StationDetail) {
    val hasFacilities = detail.hasConvenienceStore || detail.hasCarWash || detail.hasMaintenance

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
            text = "🏪 편의시설",
            style = AppTextStyles.headlineMedium.copy(fontSize = 18.sp, fontWeight = FontWeight.Bold),
            color = AppColors.Gray900
        )

        if (hasFacilities) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                if (detail.hasConvenienceStore) {
                    DetailFacilityTag(text = "🛒 편의점", bgColor = Color(0xFFF0FDF4), textColor = Color(0xFF0D542B))
                }
                if (detail.hasCarWash) {
                    DetailFacilityTag(text = "🚿 세차", bgColor = AppColors.TagCarWashBg, textColor = AppColors.TagCarWashText)
                }
                if (detail.hasMaintenance) {
                    DetailFacilityTag(text = "🔧 정비", bgColor = AppColors.TagMaintenanceBg, textColor = AppColors.TagMaintenanceText)
                }
            }
        } else {
            Text(
                text = "편의시설 정보가 없습니다.",
                style = AppTextStyles.bodySmall,
                color = AppColors.Gray500
            )
        }
    }
}
