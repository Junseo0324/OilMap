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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.devhjs.oilmap.presentation.designsystem.AppColors
import com.devhjs.oilmap.presentation.designsystem.AppTextStyles
import com.devhjs.oilmap.presentation.home.GasStationUiModel


@Composable
fun GasStationCard(
    station: GasStationUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isLowest = station.isLowestPrice
    val borderColor = if (isLowest) AppColors.AlteulMain else AppColors.Border
    val borderWidth = 1.dp
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(borderWidth, borderColor, RoundedCornerShape(16.dp))
            .clickable { onClick() }
    ) {
        Column {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (isLowest) Brush.horizontalGradient(
                            colors = listOf(AppColors.AlteulMain, AppColors.AlteulDark)
                        ) else Brush.horizontalGradient(
                            colors = listOf(AppColors.Background, AppColors.Background)
                        )
                    )
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 브랜드 이름
                Text(
                    text = station.brand,
                    style = AppTextStyles.listCountBold,
                    color = if (isLowest) Color.White else AppColors.Gray800
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                // 브랜드 아이콘 배지 (예시로 주황색 타원 등)
                Box(
                    modifier = Modifier
                        .background(AppColors.BadgeBrandBgDefault, RoundedCornerShape(10.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = station.brand,
                        style = AppTextStyles.labelSmall,
                        color = Color.White
                    )
                }
                
                Spacer(modifier = Modifier.weight(1f))
                
                // 최저가 뱃지
                if (isLowest) {
                    Box(
                        modifier = Modifier
                            .background(Color.White, RoundedCornerShape(10.dp))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "최저가",
                            style = AppTextStyles.lowestPriceBadge,
                            color = AppColors.AlteulDark
                        )
                    }
                }
                
                // 영업종료
                if (!station.isOpen) {
                    Box(
                        modifier = Modifier
                            .background(AppColors.BadgeClosedBg, RoundedCornerShape(10.dp))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "영업종료",
                            style = AppTextStyles.lowestPriceBadge,
                            color = Color.White
                        )
                    }
                }
            }
            
            // Body
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                // 주유소명
                Text(
                    text = station.name,
                    style = AppTextStyles.headlineMedium,
                    color = AppColors.Gray900
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // 가격 및 거리 정보
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "%,d".format(station.price),
                                style = AppTextStyles.priceDisplay,
                                color = AppColors.Gray900
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "원",
                                style = AppTextStyles.titleLarge,
                                color = AppColors.Gray600,
                                modifier = Modifier.padding(bottom = 6.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = station.baseTime,
                            style = AppTextStyles.captionMedium,
                            color = AppColors.Gray500
                        )
                    }
                    
                    Column(horizontalAlignment = Alignment.End) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // 거리 아이콘 영역
                            Text(
                                text = "${station.distance}km",
                                style = AppTextStyles.priceDetailDisplay,
                                color = AppColors.Gray700
                            )
                        }
                        Text(
                            text = "거리",
                            style = AppTextStyles.captionMedium,
                            color = AppColors.Gray500
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Services
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, AppColors.Border, RoundedCornerShape(8.dp))
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (station.hasCarWash) {
                        ServiceTag(text = "세차", bgColor = AppColors.TagCarWashBg, textColor = AppColors.TagCarWashText)
                    }
                    if (station.hasMaintenance) {
                        ServiceTag(text = "정비", bgColor = AppColors.TagMaintenanceBg, textColor = AppColors.TagMaintenanceText)
                    }
                    if (station.hasConvenienceStore) {
                        ServiceTag(text = "편의점", bgColor = AppColors.TagConvenienceBg, textColor = AppColors.TagConvenienceText)
                    }
                }
            }
        }
    }
}
