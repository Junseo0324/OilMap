package com.devhjs.oilmap.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.devhjs.oilmap.presentation.designsystem.AppColors
import com.devhjs.oilmap.presentation.designsystem.AppTextStyles
import com.devhjs.oilmap.presentation.map.MapStationUiModel

@Composable
fun StationMarkerInfoWindow(
    uiModel: MapStationUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val station = uiModel.station

    Column(
        modifier = modifier
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = station.name,
            style = AppTextStyles.labelMedium,
            color = AppColors.Gray900
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "%,d원".format(station.price),
            style = AppTextStyles.titleLarge,
            color = AppColors.Gray900
        )
        Spacer(modifier = Modifier.height(2.dp))
        val distanceKm = (station.distance ?: 0.0) / 1000.0
        Text(
            text = "${String.format("%.1f", distanceKm)}km",
            style = AppTextStyles.bodySmall,
            color = AppColors.AlteulMain
        )
    }
}
