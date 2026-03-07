package com.devhjs.oilmap.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.oilmap.core.util.LocationUtil
import com.devhjs.oilmap.domain.location.LocationTracker
import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.usecase.GetFavoriteStationsUseCase
import com.devhjs.oilmap.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.sqrt

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteStationsUseCase: GetFavoriteStationsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _state = MutableStateFlow(FavoriteState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<FavoriteEvent>()
    val event = _event.asSharedFlow()

    private var userKatecX: Double? = null
    private var userKatecY: Double? = null
    private var observeJob: Job? = null

    init {
        viewModelScope.launch {
            loadUserLocation()
            observeFavorites(_state.value.selectedOilType)
        }
    }

    fun onAction(action: FavoriteAction) {
        when (action) {
            is FavoriteAction.OnStationClick -> {
                viewModelScope.launch {
                    _event.emit(FavoriteEvent.NavigateToStationDetail(action.stationId))
                }
            }
            is FavoriteAction.OnResourceTypeSelected -> {
                _state.update { it.copy(selectedOilType = action.oilType) }
                observeFavorites(action.oilType)
            }
            is FavoriteAction.OnToggleFavorite -> {
                viewModelScope.launch {
                    toggleFavoriteUseCase(action.station)
                }
            }
            is FavoriteAction.OnBackClick -> {
                viewModelScope.launch {
                    _event.emit(FavoriteEvent.NavigateBack)
                }
            }
        }
    }

    private suspend fun loadUserLocation() {
        val location = locationTracker.getCurrentLocation()
        if (location != null) {
            val (katecX, katecY) = LocationUtil.wgs84ToKatec(location.latitude, location.longitude)
            userKatecX = katecX
            userKatecY = katecY
        }
    }

    // 유종 변경 시 기존 Flow 구독을 취소하고 새로운 oilType으로 재구독
    private fun observeFavorites(oilType: OilType) {
        observeJob?.cancel()
        observeJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            getFavoriteStationsUseCase(oilType).collect { stations ->
                val kx = userKatecX
                val ky = userKatecY
                val stationsWithDistance = stations.map { station ->
                    val sx = station.x
                    val sy = station.y
                    if (kx != null && ky != null && sx != null && sy != null && sx > 0 && sy > 0) {
                        val distance = sqrt((sx - kx) * (sx - kx) + (sy - ky) * (sy - ky))
                        station.copy(distance = distance)
                    } else station
                }
                _state.update {
                    it.copy(
                        stations = stationsWithDistance,
                        totalCount = stationsWithDistance.size,
                        isLoading = false
                    )
                }
            }
        }
    }
}
