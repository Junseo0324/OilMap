package com.devhjs.oilmap.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devhjs.oilmap.domain.model.Station
import com.devhjs.oilmap.presentation.component.GasStationCard
import com.devhjs.oilmap.presentation.component.HomeHeader
import com.devhjs.oilmap.presentation.designsystem.AppColors

@Composable
fun Homescreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HomeHeader(
                state = state,
                onAction = onAction
            )
            
            LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.stations, key = { it.id }) { station ->
                    GasStationCard(
                        station = station,
                        onClick = { onAction(HomeAction.OnStationClick(station.id)) }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val dummyStations = listOf(
        Station("1", "농협알뜰 도곡점", "알뜰주유소", 1538, 1.0, hasConvenienceStore = true),
        Station("2", "자영알뜰 서초점", "자영알뜰", 1545, 1.2, hasConvenienceStore = true),
        Station("3", "SK에너지 역삼주유소", "SK에너지", 1565, 0.5, hasCarWash = true, hasConvenienceStore = true),

    )
    Homescreen(
        state = HomeState(stations = dummyStations, totalCount = 8),
        onAction = {}
    )
}