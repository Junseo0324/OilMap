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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devhjs.oilmap.R
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
                        painter = painterResource(R.drawable.arrow_back),
                        contentDescription = "뒤로가기",
                        tint = AppColors.Gray900
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "즐겨찾기",
                    style = AppTextStyles.headlineMedium.copy(color = AppColors.Gray900, fontWeight = FontWeight.Bold),
                )
            }

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
                    icon = R.drawable.gasoline,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onAction(FavoriteAction.OnResourceTypeSelected("휘발유")) }
                )
                FuelTypeButton(
                    text = "경유",
                    isSelected = state.selectedResourceType == "경유",
                    icon = R.drawable.diesel,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onAction(FavoriteAction.OnResourceTypeSelected("경유")) }
                )
                FuelTypeButton(
                    text = "LPG",
                    isSelected = state.selectedResourceType == "LPG",
                    icon = R.drawable.lpg,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onAction(FavoriteAction.OnResourceTypeSelected("LPG")) }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "총 ",
                    style = AppTextStyles.listCountNormal.copy(color = AppColors.Gray700),
                )
                Text(
                    text = "${state.totalCount}개",
                    style = AppTextStyles.listCountBold.copy(color = AppColors.Gray900),
                )
                Text(
                    text = " 즐겨찾기",
                    style = AppTextStyles.listCountNormal.copy(color = AppColors.Gray700),
                )
            }

            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = AppColors.AlteulMain)
                }
            } else if (state.stations.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(R.drawable.star_filled),
                            contentDescription = null,
                            tint = AppColors.FavoriteIcon,
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
                    items(state.stations, key = { it.id }) { station ->
                        FavoriteStationCard(
                            station = station,
                            onClick = { onAction(FavoriteAction.OnStationClick(station.id)) },
                            onToggleFavorite = { onAction(FavoriteAction.OnToggleFavorite(station)) }
                        )
                    }
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
            Station(
                id = "1",
                name = "알뜰주유소 도곡점",
                brandCode = "RTE",
                price = 1538,
                distance = 1000.0,
                isFavorite = true
            )
    )
    FavoriteScreen(
        state = FavoriteState(stations = dummyStations, totalCount = 1),
        onAction = {}
    )
}

@Preview(showBackground = true)
@Composable
fun FavoriteScreenPreviewNotData() {
    FavoriteScreen(
        state = FavoriteState(stations = emptyList(), totalCount = 1),
        onAction = {}
    )
}