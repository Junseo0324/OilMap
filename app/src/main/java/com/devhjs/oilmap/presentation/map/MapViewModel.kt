package com.devhjs.oilmap.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.oilmap.core.util.LocationUtil
import com.devhjs.oilmap.core.util.Result
import com.devhjs.oilmap.domain.location.LocationTracker
import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.model.SortType
import com.devhjs.oilmap.domain.usecase.GetAroundStationsUseCase
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getAroundStationsUseCase: GetAroundStationsUseCase,
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _state = MutableStateFlow(MapState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<MapEvent>()
    val event = _event.asSharedFlow()

    init {
        fetchStations()
    }

    fun onAction(action: MapAction) {
        when (action) {
            is MapAction.OnResourceTypeSelected -> {
                _state.update { it.copy(selectedOilType = action.oilType) }
                fetchStations(oilType = action.oilType)
            }
            is MapAction.OnSortOptionSelected -> {
                _state.update { it.copy(selectedSortType = action.sortType) }
                fetchStations(sortType = action.sortType)
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
        }
    }

    private fun fetchStations(
        oilType: OilType = _state.value.selectedOilType,
        sortType: SortType = _state.value.selectedSortType
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
                    oilType = oilType,
                    sortType = sortType
                )
                when (result) {
                    is Result.Success -> {
                        val uiStations = result.data.mapIndexed { index, station ->
                            val x = station.x ?: 0.0
                            val y = station.y ?: 0.0
                            val (lat, lng) = LocationUtil.katecToWgs84(x, y)
                            MapStationUiModel(
                                station = station,
                                latLng = LatLng(lat, lng),
                                isLowestPrice = sortType == SortType.PRICE && index == 0
                            )
                        }
                        _state.update {
                            it.copy(
                                isLoading = false,
                                stations = uiStations,
                                totalCount = uiStations.size
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
