package com.devhjs.oilmap.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.oilmap.core.util.Result
import com.devhjs.oilmap.domain.location.LocationTracker
import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.model.SortType
import com.devhjs.oilmap.domain.model.Station
import com.devhjs.oilmap.domain.usecase.GetAroundStationsUseCase
import com.devhjs.oilmap.domain.usecase.GetUserPreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAroundStationsUseCase: GetAroundStationsUseCase,
    private val getUserPreferenceUseCase: GetUserPreferenceUseCase,
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<HomeEvent>()
    val event = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            getUserPreferenceUseCase().collect { prefs ->
                val prevOilType = _state.value.selectedOilType
                val prevSortType = _state.value.selectedSortType
                val prevRadius = _state.value.searchRadius

                val wasLoaded = _state.value.isPreferencesLoaded

                _state.update {
                    it.copy(
                        selectedOilType = prefs.favoriteOilType,
                        selectedSortType = prefs.defaultSortType,
                        searchRadius = prefs.searchRadius,
                        isPreferencesLoaded = true
                    )
                }

                if (!wasLoaded) {
                    fetchStations()
                } else if (prevOilType != prefs.favoriteOilType ||
                    prevSortType != prefs.defaultSortType ||
                    prevRadius != prefs.searchRadius
                ) {
                    fetchStations()
                }
            }
        }
    }

    fun onAction(action: HomeAction) {
        when(action) {
            is HomeAction.OnResourceTypeSelected -> {
                _state.update { it.copy(selectedOilType = action.oilType) }
                fetchStations(oilType = action.oilType)
            }
            is HomeAction.OnSortOptionSelected -> {
                _state.update {
                    it.copy(
                        selectedSortType = action.sortType,
                        sortedStations = sortStations(it.stations, action.sortType)
                    )
                }
            }
            is HomeAction.OnStationClick -> {
                viewModelScope.launch {
                    _event.emit(HomeEvent.NavigateToStationDetail(action.stationId))
                }
            }
            is HomeAction.OnPermissionGranted -> {
                fetchStations()
            }
            is HomeAction.OnSettingsClick -> {
                viewModelScope.launch {
                    _event.emit(HomeEvent.NavigateToSettings)
                }
            }
        }
    }

    private fun sortStations(stations: List<Station>, sortType: SortType): List<Station> {
        return when (sortType) {
            SortType.PRICE -> stations.sortedBy { it.price }
            SortType.DISTANCE -> stations.sortedBy { it.distance }
        }
    }

    private fun fetchStations(
        oilType: OilType = _state.value.selectedOilType,
        sortType: SortType = _state.value.selectedSortType,
        searchRadius: Int = _state.value.searchRadius
    ) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val location = locationTracker.getCurrentLocation()
            if (location != null) {
                val result = getAroundStationsUseCase(
                    lat = location.latitude,
                    lng = location.longitude,
                    radius = searchRadius,
                    oilType = oilType,
                    sortType = sortType
                )
                when(result) {
                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                stations = result.data,
                                sortedStations = sortStations(result.data, sortType),
                                totalCount = result.data.size
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
