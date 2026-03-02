package com.devhjs.oilmap.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devhjs.oilmap.presentation.designsystem.AppColors
import com.devhjs.oilmap.presentation.designsystem.AppTextStyles

/**
 * Figma의 즐겨찾기 안내 배너
 * 초록색 배경 + ℹ 아이콘 + 안내 문구
 */
@Composable
fun FavoriteInfoBanner(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(AppColors.FavoriteBannerBg, RoundedCornerShape(10.dp))
            .border(1.dp, AppColors.FavoriteBannerBorder, RoundedCornerShape(10.dp))
            .padding(horizontal = 17.dp, vertical = 17.dp),
        verticalAlignment = Alignment.Top
    ) {
        // ℹ 아이콘
        Icon(
            imageVector = Icons.Outlined.Info,
            contentDescription = "안내",
            tint = AppColors.FavoriteBannerBody,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            // 제목
            Text(
                text = "즐겨찾기 안내",
                style = AppTextStyles.labelMedium,
                color = AppColors.FavoriteBannerTitle
            )
            Spacer(modifier = Modifier.height(4.dp))
            // 본문
            Text(
                text = "즐겨찾기는 이 기기에만 저장됩니다. 앱을 삭제하거나 브라우저 데이터를 삭제하면 초기화됩니다.",
                style = AppTextStyles.bodySmall,
                color = AppColors.FavoriteBannerBody
            )
        }
    }
}
