package com.devhjs.oilmap.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.oilmap.R
import com.devhjs.oilmap.presentation.designsystem.AppColors
import com.devhjs.oilmap.presentation.designsystem.AppTextStyles

@Composable
fun DetailTopBar(
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .shadow(2.dp)
            .background(Color.White)
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "뒤로가기",
                tint = AppColors.Gray800
            )
        }
        Text(
            text = "주유소 상세",
            style = AppTextStyles.headlineMedium.copy(fontSize = 20.sp),
            color = Color(0xFF0A0A0A)
        )
        IconButton(onClick = onFavoriteClick) {
            Icon(
                painter = painterResource(
                    if (isFavorite) R.drawable.star_filled else R.drawable.star_default
                ),
                contentDescription = "즐겨찾기",
                tint = if (isFavorite) AppColors.FavoriteIcon else AppColors.Gray500
            )
        }
    }
}
