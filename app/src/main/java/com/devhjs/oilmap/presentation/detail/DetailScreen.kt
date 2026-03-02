package com.devhjs.oilmap.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.devhjs.oilmap.presentation.component.DetailActionButtons
import com.devhjs.oilmap.presentation.component.DetailFacilityCard
import com.devhjs.oilmap.presentation.component.DetailInfoCard
import com.devhjs.oilmap.presentation.component.DetailPriceCard
import com.devhjs.oilmap.presentation.component.DetailTopBar
import com.devhjs.oilmap.presentation.designsystem.AppColors
import com.devhjs.oilmap.presentation.designsystem.AppTextStyles

@Composable
fun DetailScreen(
    state: DetailState,
    onAction: (DetailAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        DetailTopBar(
            isFavorite = state.stationDetail?.isFavorite ?: false,
            onBackClick = { onAction(DetailAction.OnBackClick) },
            onFavoriteClick = { onAction(DetailAction.OnFavoriteToggle) }
        )

        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = AppColors.AlteulMain)
            }
        } else if (state.stationDetail != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppColors.Background)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DetailInfoCard(detail = state.stationDetail, onCallClick = { onAction(DetailAction.OnCallClick) })

                DetailPriceCard(detail = state.stationDetail)

                DetailFacilityCard(detail = state.stationDetail)

                DetailActionButtons(
                    onNavigateClick = { onAction(DetailAction.OnNavigateClick) },
                    onRefreshClick = { onAction(DetailAction.OnRefreshClick) }
                )
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.error ?: "데이터를 불러올 수 없습니다.",
                    style = AppTextStyles.bodySmall,
                    color = AppColors.Gray600
                )
            }
        }
    }
}