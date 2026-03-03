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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.devhjs.oilmap.R
import com.devhjs.oilmap.presentation.component.FuelTypeButton
import com.devhjs.oilmap.presentation.component.StationMarkerInfoWindow
import com.devhjs.oilmap.presentation.designsystem.AppColors
import com.devhjs.oilmap.presentation.designsystem.AppTextStyles
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MarkerInfoWindowContent
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(
    state: MapState,
    onAction: (MapAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val defaultLocation = LatLng(37.4979, 127.0276)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            state.currentLocation ?: defaultLocation, 14f
        )
    }

    var isMapLoaded by remember { mutableStateOf(false) }

    // 현재 위치 변경 시 카메라 이동 (지도가 완전히 로드된 후에만 수행)
    LaunchedEffect(state.currentLocation, isMapLoaded) {
        if (isMapLoaded) {
            state.currentLocation?.let { location ->
                cameraPositionState.animate(
                    CameraUpdateFactory.newLatLngZoom(location, 14f)
                )
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.Background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // ===== 헤더: 앱 타이틀 + 즐겨찾기 아이콘 =====
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
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "즐겨찾기",
                        tint = AppColors.Gray800
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 유종 탭
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FuelTypeButton(
                        text = "휘발유",
                        isSelected = state.selectedResourceType == "휘발유",
                        icon = R.drawable.gasoline,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onAction(MapAction.OnResourceTypeSelected("휘발유")) }
                    )
                    FuelTypeButton(
                        text = "경유",
                        isSelected = state.selectedResourceType == "경유",
                        icon = R.drawable.diesel,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onAction(MapAction.OnResourceTypeSelected("경유")) }
                    )
                    FuelTypeButton(
                        text = "LPG",
                        isSelected = state.selectedResourceType == "LPG",
                        icon = R.drawable.lpg,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onAction(MapAction.OnResourceTypeSelected("LPG")) }
                    )
                }

            }

            // ===== 지도 영역 =====
            Box(
                modifier = Modifier.weight(1f).fillMaxWidth()
            )
            {
                if (state.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = AppColors.AlteulMain)
                    }
                } else {
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState,
                        properties = MapProperties(
                            isMyLocationEnabled = true
                        ),
                        onMapLoaded = { isMapLoaded = true }
                    ) {
                        state.stations.forEach { uiModel ->
                            MarkerInfoWindowContent(
                                state = MarkerState(position = uiModel.latLng),
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
            }
        }
    }
}