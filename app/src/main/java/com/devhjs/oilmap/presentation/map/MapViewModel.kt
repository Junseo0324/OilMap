package com.devhjs.oilmap.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.oilmap.core.util.LocationUtil
import com.devhjs.oilmap.core.util.Result
import com.devhjs.oilmap.domain.location.LocationTracker
import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.model.SortType
import com.devhjs.oilmap.domain.usecase.GetAroundStationsUseCase
import com.devhjs.oilmap.domain.usecase.GetUserPreferenceUseCase
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getAroundStationsUseCase: GetAroundStationsUseCase,
    private val getUserPreferenceUseCase: GetUserPreferenceUseCase,
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _state = MutableStateFlow(MapState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<MapEvent>()
    val event = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            val prefs = getUserPreferenceUseCase().first()
            _state.update {
                it.copy(
                    selectedOilType = prefs.favoriteOilType,
                    searchRadius = prefs.searchRadius
                )
            }
            fetchStations()
        }
    }

    fun onAction(action: MapAction) {
        when (action) {
            is MapAction.OnResourceTypeSelected -> {
                _state.update { it.copy(selectedOilType = action.oilType) }
                fetchStations(oilType = action.oilType)
            }
            is MapAction.OnStationClick -> {
                viewModelScope.launch {
                    _event.emit(MapEvent.NavigateToStationDetail(action.stationId))
                }
            }
            is MapAction.OnMarkerClick -> {
                _state.update { it.copy(selectedStationId = action.stationId) }
            }
            is MapAction.OnPermissionGranted -> {
                fetchStations()
            }
            is MapAction.OnMapLoaded -> {
                _state.update { it.copy(isMapLoaded = true) }
            }
            is MapAction.OnSettingsClick -> {
                viewModelScope.launch {
                    _event.emit(MapEvent.NavigateToSettings)
                }
            }
        }
    }

    private fun fetchStations(
        oilType: OilType = _state.value.selectedOilType,
        searchRadius: Int = _state.value.searchRadius
    ) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val location = locationTracker.getCurrentLocation()
            if (location != null) {
                _state.update {
                    it.copy(currentLocation = LatLng(location.latitude, location.longitude))
                }

                val result = getAroundStationsUseCase(
                    lat = location.latitude,
                    lng = location.longitude,
                    radius = searchRadius,
                    oilType = oilType,
                    sortType = SortType.DISTANCE
                )
                when (result) {
                    is Result.Success -> {
                        val lowestPrice = result.data.minOfOrNull { it.price } ?: 0
                        val uiStations = result.data.map { station ->
                            val x = station.x ?: 0.0
                            val y = station.y ?: 0.0
                            val (lat, lng) = LocationUtil.katecToWgs84(x, y)
                            MapStationUiModel(
                                station = station,
                                latLng = LatLng(lat, lng),
                                isLowestPrice = station.price == lowestPrice
                            )
                        }
                        _state.update {
                            it.copy(
                                isLoading = false,
                                stations = uiStations
                            )
                        }
                    }
                    is Result.Error -> {
                        _state.update { it.copy(isLoading = false) }
                    }
                }
            } else {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}
