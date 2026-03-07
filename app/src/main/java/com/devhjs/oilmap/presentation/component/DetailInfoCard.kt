package com.devhjs.oilmap.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.oilmap.R
import com.devhjs.oilmap.core.util.getBrandName
import com.devhjs.oilmap.domain.model.StationDetail
import com.devhjs.oilmap.presentation.designsystem.AppColors
import com.devhjs.oilmap.presentation.designsystem.AppTextStyles

@Composable
fun DetailInfoCard(
    detail: StationDetail,
    onCallClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(14.dp))
            .background(Color.White, RoundedCornerShape(14.dp))
            .border(1.dp, AppColors.Border, RoundedCornerShape(14.dp))
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = getBrandName(detail.brandCode),
                style = AppTextStyles.bodySmall.copy(fontWeight = FontWeight.Medium),
                color = AppColors.Gray600
            )
        }

        // 주유소 이름
        Text(
            text = detail.name,
            style = AppTextStyles.headlineLarge.copy(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            color = AppColors.Gray900
        )

        // 주소 섹션
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColors.Background, RoundedCornerShape(10.dp))
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.location),
                contentDescription = "주소",
                tint = AppColors.Gray600
            )
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = "주소", style = AppTextStyles.bodySmall, color = AppColors.Gray600)
                Text(
                    text = detail.address.ifEmpty { "주소 정보 없음" },
                    style = AppTextStyles.bodySmall.copy(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                    color = AppColors.Gray900
                )
                val distanceKm = (detail.distance ?: 0.0) / 1000.0
                Text(
                    text = "${String.format("%.1f", distanceKm)}km 거리",
                    style = AppTextStyles.bodySmall,
                    color = AppColors.Gray600
                )
            }
        }

        // 전화번호 섹션
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColors.Background, RoundedCornerShape(10.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.phone),
                contentDescription = "전화번호",
                tint = AppColors.Gray600
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = "전화번호", style = AppTextStyles.bodySmall, color = AppColors.Gray600)
                Text(
                    text = detail.tel.ifEmpty { "전화번호 없음" },
                    style = AppTextStyles.bodySmall.copy(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                    color = AppColors.Gray900
                )
            }
            // 전화 버튼
            if (detail.tel.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(AppColors.ActionButton)
                        .clickable { onCallClick() }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "전화",
                        style = AppTextStyles.bodySmall.copy(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                }
            }
        }

        // 기준 시간
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.time),
                contentDescription = "시간",
                tint = AppColors.Gray600
            )
            Text(
                text = "09:00 기준",
                style = AppTextStyles.bodySmall,
                color = AppColors.Gray600
            )
        }
    }
}


