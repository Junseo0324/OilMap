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


@Composable
fun GasStationCard(
    modifier: Modifier = Modifier,
    station: Station,
    onClick: () -> Unit= {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, AppColors.Border, RoundedCornerShape(16.dp))
            .clickable { onClick() }
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(AppColors.Background, AppColors.Background)
                        )
                    )
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = getBrandName(station.brandCode),
                    style = AppTextStyles.listCountBold,
                    color = AppColors.Gray800
                )
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
                
                val hasAnyService = station.hasCarWash || station.hasMaintenance || station.hasConvenienceStore
                if (hasAnyService) {
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
}

@Preview
@Composable
private fun GasStationCardPreview() {
    GasStationCard(
        station = Station("1", "농협알뜰 도곡점", "알뜰주유소", 1538,)
    )
}