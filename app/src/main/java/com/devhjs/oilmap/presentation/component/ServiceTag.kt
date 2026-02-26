package com.devhjs.oilmap.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.devhjs.oilmap.presentation.designsystem.AppTextStyles

@Composable
fun ServiceTag(
    text: String,
    bgColor: Color,
    textColor: Color
) {
    Row(
        modifier = Modifier
            .background(bgColor, RoundedCornerShape(6.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 아이콘 자리
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = AppTextStyles.labelExtraSmall.copy(fontWeight = FontWeight.Medium),
            color = textColor
        )
    }
}