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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devhjs.oilmap.core.util.getBrandName
import com.devhjs.oilmap.domain.model.Station
import com.devhjs.oilmap.presentation.designsystem.AppColors
import com.devhjs.oilmap.presentation.designsystem.AppTextStyles
import com.devhjs.oilmap.presentation.home.GasStationUiModel


@Composable
fun GasStationCard(
    modifier: Modifier = Modifier,
    uiModel: GasStationUiModel,
    onClick: () -> Unit= {},
) {
    val isLowestPrice = uiModel.isLowestPrice
    val station = uiModel.station
    
    val borderColor = if (isLowestPrice) AppColors.AlteulMain else AppColors.Border
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (isLowestPrice) Brush.horizontalGradient(
                            colors = listOf(AppColors.AlteulMain, AppColors.AlteulDark)
                        ) else Brush.horizontalGradient(
                            colors = listOf(AppColors.Background, AppColors.Background)
                        )
                    )
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = getBrandName(station.brandCode),
                    style = AppTextStyles.listCountBold,
                    color = if (isLowestPrice) Color.White else AppColors.Gray800
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Box(
                    modifier = Modifier
                        .background(AppColors.BadgeBrandBgDefault, RoundedCornerShape(10.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = station.brandCode,
                        style = AppTextStyles.labelSmall,
                        color = Color.White
                    )
                }
                
                Spacer(modifier = Modifier.weight(1f))
                
                if (isLowestPrice) {
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
            }
            
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = station.name,
                    style = AppTextStyles.headlineMedium,
                    color = AppColors.Gray900
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
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
                            text = "10:00 기준",
                            style = AppTextStyles.captionMedium,
                            color = AppColors.Gray500
                        )
                    }
                    
                    Column(horizontalAlignment = Alignment.End) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            val distanceKm = (station.distance ?: 0.0) / 1000.0
                            Text(
                                text = "${String.format("%.1f", distanceKm)}km",
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

@Preview
@Composable
private fun GasStationCardPreview() {
    GasStationCard(
        uiModel = GasStationUiModel(
            station = Station(
                "1", "농협알뜰 도곡점", "알뜰주유소", 1538,
            )
        )
    )
}