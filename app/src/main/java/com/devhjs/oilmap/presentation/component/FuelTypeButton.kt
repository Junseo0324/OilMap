package com.devhjs.oilmap.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.devhjs.oilmap.presentation.designsystem.AppColors
import com.devhjs.oilmap.presentation.designsystem.AppTextStyles

@Composable
fun FuelTypeButton(
    text: String,
    isSelected: Boolean,
    iconVector: ImageVector,
    modifier: Modifier = Modifier
) {
    val bgColor = if (isSelected) AppColors.AlteulDark else AppColors.Background
    val textColor = if (isSelected) Color.White else AppColors.Gray800
    
    Row(
        modifier = modifier
            .height(44.dp)
            .background(bgColor, RoundedCornerShape(10.dp))
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = iconVector,
            contentDescription = text,
            tint = textColor,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = AppTextStyles.bodyMedium,
            color = textColor
        )
    }
}