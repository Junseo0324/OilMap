package com.devhjs.oilmap.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.devhjs.oilmap.R
import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.model.SortType
import com.devhjs.oilmap.presentation.component.SettingSection
import com.devhjs.oilmap.presentation.designsystem.AppColors
import com.devhjs.oilmap.presentation.designsystem.AppTextStyles


@Composable
fun SettingsScreen(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = "Back",
                tint = AppColors.Gray600,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onAction(SettingsAction.OnBackClick) }
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "설정",
                style = AppTextStyles.headlineLarge.copy(color = Color(0xFF0A0A0A))
            )
        }

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = AppColors.AlteulMain)
            }
            return
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            SettingSection(title = "선호 유종") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val oilTypes = listOf(OilType.GASOLINE, OilType.DIESEL, OilType.LPG)
                    oilTypes.forEach { oilType ->
                        val isSelected = state.favoriteOilType == oilType
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .background(
                                    if (isSelected) AppColors.AlteulMain else Color.White,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { onAction(SettingsAction.OnFavoriteOilTypeChanged(oilType)) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = oilType.displayName,
                                style = AppTextStyles.bodyMedium.copy(fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal),
                                color = if (isSelected) Color.White else AppColors.Gray600
                            )
                        }
                    }
                }
            }

            SettingSection(title = "기본 정렬 기준") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val sortTypes = listOf(SortType.DISTANCE, SortType.PRICE)
                    sortTypes.forEach { sortType ->
                        val isSelected = state.defaultSortType == sortType
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .background(
                                    if (isSelected) AppColors.AlteulMain else Color.White,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { onAction(SettingsAction.OnDefaultSortTypeChanged(sortType)) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = sortType.displayName,
                                style = AppTextStyles.bodyMedium.copy(fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal),
                                color = if (isSelected) Color.White else AppColors.Gray600
                            )
                        }
                    }
                }
            }

            SettingSection(title = "검색 반경") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val radiusList = listOf(1000 to "1km", 3000 to "3km", 5000 to "5km")
                    radiusList.forEach { (radiusValue, label) ->
                        val isSelected = state.searchRadius == radiusValue
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .background(
                                    if (isSelected) AppColors.AlteulMain else Color.White,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { onAction(SettingsAction.OnSearchRadiusChanged(radiusValue)) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = label,
                                style = AppTextStyles.bodyMedium.copy(fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal),
                                color = if (isSelected) Color.White else AppColors.Gray600
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { onAction(SettingsAction.OnResetPreferences) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.Border),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "설정 초기화",
                    style = AppTextStyles.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = AppColors.Gray700
                )
            }
        }
    }
}
