package com.devhjs.oilmap.presentation.map

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.devhjs.oilmap.R
import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.presentation.component.FuelTypeButton
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

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "OilMap",
                        style = AppTextStyles.headlineLarge,
                        color = Color(0xFF0A0A0A)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 유종 탭
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FuelTypeButton(
                        text = OilType.GASOLINE.displayName,
                        isSelected = state.selectedOilType == OilType.GASOLINE,
                        icon = R.drawable.gasoline,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onAction(MapAction.OnResourceTypeSelected(OilType.GASOLINE)) }
                    )
                    FuelTypeButton(
                        text = OilType.DIESEL.displayName,
                        isSelected = state.selectedOilType == OilType.DIESEL,
                        icon = R.drawable.diesel,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onAction(MapAction.OnResourceTypeSelected(OilType.DIESEL)) }
                    )
                    FuelTypeButton(
                        text = OilType.LPG.displayName,
                        isSelected = state.selectedOilType == OilType.LPG,
                        icon = R.drawable.lpg,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onAction(MapAction.OnResourceTypeSelected(OilType.LPG)) }
                    )
                }

            }

            // ===== 지도 영역 =====
            Box(
                modifier = Modifier.weight(1f).fillMaxWidth()
            )
            {
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