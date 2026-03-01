package com.devhjs.oilmap.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.oilmap.presentation.designsystem.AppColors
import com.devhjs.oilmap.presentation.designsystem.AppTextStyles

@Composable
fun DetailPriceRow(
    label: String,
    subLabel: String,
    initial: String,
    price: Int,
    color: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.Background, RoundedCornerShape(10.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 원형 아이콘
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(color, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = initial,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
            Column {
                Text(
                    text = label,
                    style = AppTextStyles.bodySmall.copy(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                    color = AppColors.Gray900
                )
                Text(
                    text = subLabel,
                    style = AppTextStyles.captionMedium,
                    color = AppColors.Gray600
                )
            }
        }
        // 가격
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = "%,d".format(price),
                style = AppTextStyles.priceDisplay.copy(fontSize = 24.sp),
                color = AppColors.Gray900
            )
            Text(
                text = "원",
                style = AppTextStyles.bodySmall.copy(fontSize = 16.sp),
                color = AppColors.Gray900,
                modifier = Modifier.padding(bottom = 2.dp)
            )
        }
    }
}
