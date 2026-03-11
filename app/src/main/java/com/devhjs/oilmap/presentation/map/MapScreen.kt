package com.devhjs.oilmap.presentation.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.devhjs.oilmap.presentation.component.MainHeader
import com.devhjs.oilmap.presentation.component.StationMarkerInfoWindow
import com.devhjs.oilmap.presentation.component.createPriceMarkerBitmap
import com.devhjs.oilmap.presentation.designsystem.AppColors
import com.devhjs.oilmap.presentation.designsystem.AppTextStyles
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerInfoWindowContent
import com.google.maps.android.compose.MarkerState

@Composable
fun MapScreen(
    state: MapState,
    cameraPositionState: CameraPositionState,
    onAction: (MapAction) -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            MainHeader(
                selectedOilType = state.selectedOilType,
                onOilTypeSelected = { onAction(MapAction.OnResourceTypeSelected(it)) },
                onSettingsClick = { onAction(MapAction.OnSettingsClick) }
            )

            Box(
                modifier = Modifier.weight(1f).fillMaxWidth()
            )
            {
                if (state.stations.isEmpty() && !state.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "주변 반경에 주유소가 없습니다.",
                            style = AppTextStyles.noDataText
                        )
                    }
                } else {
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState,
                        properties = MapProperties(
                            isMyLocationEnabled = true
                        ),
                        uiSettings = MapUiSettings(
                            mapToolbarEnabled = false
                        ),
                        onMapLoaded = { onAction(MapAction.OnMapLoaded) }
                    ) {
                    state.stations.forEach { uiModel ->
                        val markerIcon = remember(uiModel.station.price, uiModel.isLowestPrice) {
                            createPriceMarkerBitmap(
                                price = uiModel.station.price,
                                isLowestPrice = uiModel.isLowestPrice
                            )
                        }
                        MarkerInfoWindowContent(
                            state = MarkerState(position = uiModel.latLng),
                            icon = markerIcon,
                            title = uiModel.station.name,
                            onInfoWindowClick = {
                                onAction(MapAction.OnStationClick(uiModel.station.id))
                            }
                        ) {
                            StationMarkerInfoWindow(
                                uiModel = uiModel,
                                onClick = { onAction(MapAction.OnStationClick(uiModel.station.id)) }
                            )
                        }
                    }
                    }
                }

                if (state.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = AppColors.AlteulMain)
                    }
                }
            }
        }
    }
}