package com.devhjs.oilmap.presentation.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devhjs.oilmap.domain.model.Station
import com.devhjs.oilmap.presentation.component.FavoriteInfoBanner
import com.devhjs.oilmap.presentation.component.FavoriteStationCard
import com.devhjs.oilmap.presentation.component.FuelTypeButton
import com.devhjs.oilmap.presentation.designsystem.AppColors
import com.devhjs.oilmap.presentation.designsystem.AppTextStyles

@Composable
fun FavoriteScreen(
    state: FavoriteState,
    onAction: (FavoriteAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onAction(FavoriteAction.OnBackClick) }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "뒤로가기",
                        tint = AppColors.Gray900
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = Color(0xFFEAB308), // yellow-500
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "즐겨찾기",
                    style = AppTextStyles.headlineMedium,
                    color = AppColors.Gray900
                )
            }

            // ===== 유종 탭 =====
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FuelTypeButton(
                    text = "휘발유",
                    isSelected = state.selectedResourceType == "휘발유",
                    iconVector = Icons.Filled.Place,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onAction(FavoriteAction.OnResourceTypeSelected("휘발유")) }
                )
                FuelTypeButton(
                    text = "경유",
                    isSelected = state.selectedResourceType == "경유",
                    iconVector = Icons.Filled.Build,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onAction(FavoriteAction.OnResourceTypeSelected("경유")) }
                )
                FuelTypeButton(
                    text = "LPG",
                    isSelected = state.selectedResourceType == "LPG",
                    iconVector = Icons.Filled.Info,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onAction(FavoriteAction.OnResourceTypeSelected("LPG")) }
                )
            }

            // ===== 카운트 텍스트: "총 N개 즐겨찾기" =====
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "총 ",
                    style = AppTextStyles.listCountNormal,
                    color = AppColors.Gray700
                )
                Text(
                    text = "${state.totalCount}개",
                    style = AppTextStyles.listCountBold,
                    color = AppColors.Gray900
                )
                Text(
                    text = " 즐겨찾기",
                    style = AppTextStyles.listCountNormal,
                    color = AppColors.Gray700
                )
            }

            // ===== 즐겨찾기 목록 =====
            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = AppColors.AlteulMain)
                }
            } else if (state.stations.isEmpty()) {
                // 빈 상태
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = AppColors.Gray500,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "즐겨찾기한 주유소가 없습니다",
                            style = AppTextStyles.noDataText,
                            color = AppColors.Gray500
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.stations, key = { it.station.id }) { uiModel ->
                        FavoriteStationCard(
                            uiModel = uiModel,
                            onClick = { onAction(FavoriteAction.OnStationClick(uiModel.station.id)) },
                            onToggleFavorite = { onAction(FavoriteAction.OnToggleFavorite(uiModel.station)) }
                        )
                    }

                    // 안내 배너 (목록 하단)
                    item {
                        Spacer(modifier = Modifier.height(4.dp))
                        FavoriteInfoBanner()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteScreenPreview() {
    val dummyStations = listOf(
        FavoriteStationUiModel(
            station = Station(
                id = "1",
                name = "알뜰주유소 도곡점",
                brandCode = "RTE",
                price = 1538,
                distance = 1000.0,
                isFavorite = true
            ),
            isOpen = true
        )
    )
    FavoriteScreen(
        state = FavoriteState(stations = dummyStations, totalCount = 1),
        onAction = {}
    )
}