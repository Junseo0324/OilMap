package com.devhjs.oilmap.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.devhjs.oilmap.core.util.getBrandName
import com.devhjs.oilmap.domain.model.Station
import com.devhjs.oilmap.presentation.designsystem.AppColors
import com.devhjs.oilmap.presentation.designsystem.AppTextStyles

/**
 * Figma 즐겨찾기 카드 컴포넌트
 * 브랜드명 + 주유소명 + ☆ + 가격 + 거리 + 업데이트 시간 + > chevron
 */
@Composable
fun FavoriteStationCard(
    station: Station,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(14.dp),
                ambientColor = Color.Black.copy(alpha = 0.1f)
            )
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
            .border(1.dp, AppColors.Border, RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .padding(20.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = getBrandName(station.brandCode),
                        style = AppTextStyles.labelMedium,
                        color = AppColors.Gray600
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = station.name,
                        style = AppTextStyles.titleLarge,
                        color = AppColors.Gray900
                    )
                }
                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "즐겨찾기 해제",
                        tint = Color(0xFFEAB308),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "%,d".format(station.price),
                        style = AppTextStyles.priceDisplay,
                        color = AppColors.Gray900
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "원",
                        style = AppTextStyles.titleLarge,
                        color = AppColors.Gray900,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "◎",
                        style = AppTextStyles.bodySmall,
                        color = AppColors.Gray600
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    val distanceKm = (station.distance ?: 0.0) / 1000.0
                    Text(
                        text = "${String.format("%.0f", distanceKm)}km",
                        style = AppTextStyles.distanceDisplay,
                        color = AppColors.Gray700
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "◷",
                        style = AppTextStyles.captionMedium,
                        color = AppColors.Gray600
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "정보 업데이트",
                        style = AppTextStyles.bodySmall,
                        color = AppColors.Gray600
                    )
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "상세보기",
                    tint = AppColors.Gray500,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
