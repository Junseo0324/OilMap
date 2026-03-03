package com.devhjs.oilmap.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.oilmap.core.util.Result
import com.devhjs.oilmap.domain.location.LocationTracker
import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.model.SortType
import com.devhjs.oilmap.domain.usecase.GetAroundStationsUseCase
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
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<HomeEvent>()
    val event = _event.asSharedFlow()

    init {
        fetchStations()
    }

    fun onAction(action: HomeAction) {
        when(action) {
            is HomeAction.OnResourceTypeSelected -> {
                val newOilType = when(action.resourceType) {
                    "휘발유" -> OilType.GASOLINE
                    "경유" -> OilType.DIESEL
                    "LPG" -> OilType.LPG
                    else -> OilType.GASOLINE
                }
                _state.update { it.copy(selectedResourceType = action.resourceType) }
                fetchStations(oilType = newOilType, sortType = currentSortType())
            }
            is HomeAction.OnSortOptionSelected -> {
                val newSortType = when(action.sortOption) {
                    "가격순" -> SortType.PRICE
                    "거리순" -> SortType.DISTANCE
                    else -> SortType.PRICE
                }
                _state.update { it.copy(selectedSortOption = action.sortOption) }
                fetchStations(oilType = currentOilType(), sortType = newSortType)
            }
            is HomeAction.OnStationClick -> {
                viewModelScope.launch {
                    _event.emit(HomeEvent.NavigateToStationDetail(action.stationId))
                }
            }
            is HomeAction.OnPermissionGranted -> {
                fetchStations()
            }
            is HomeAction.OnFavoriteClick -> {
                viewModelScope.launch {
                    _event.emit(HomeEvent.NavigateToFavorite)
                }
            }
        }
    }

    private fun currentOilType(): OilType {
        return when(_state.value.selectedResourceType) {
            "휘발유" -> OilType.GASOLINE
            "경유" -> OilType.DIESEL
            "LPG" -> OilType.LPG
            else -> OilType.GASOLINE
        }
    }

    private fun currentSortType(): SortType {
        return when(_state.value.selectedSortOption) {
            "가격순" -> SortType.PRICE
            "거리순" -> SortType.DISTANCE
            else -> SortType.PRICE
        }
    }

    private fun fetchStations(
        oilType: OilType = currentOilType(),
        sortType: SortType = currentSortType()
    ) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val location = locationTracker.getCurrentLocation()
            if (location != null) {
                val result = getAroundStationsUseCase(
                    lat = location.latitude,
                    lng = location.longitude,
                    oilType = oilType,
                    sortType = sortType
                )
                when(result) {
                    is Result.Success -> {
                        val uiStations = result.data.mapIndexed { index, station ->
                            GasStationUiModel(
                                station = station,
                                isLowestPrice = sortType == SortType.PRICE && index == 0,
                                isOpen = true // 추후 로직 필요 시 업데이트
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
