package com.devhjs.oilmap.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.oilmap.core.util.LocationUtil
import com.devhjs.oilmap.core.util.Result
import com.devhjs.oilmap.domain.location.LocationTracker
import com.devhjs.oilmap.domain.model.Station
import com.devhjs.oilmap.domain.usecase.GetStationDetailUseCase
import com.devhjs.oilmap.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getStationDetailUseCase: GetStationDetailUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val locationTracker: LocationTracker,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val stationId: String = savedStateHandle["stationId"] ?: ""

    private val _state = MutableStateFlow(DetailState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<DetailEvent>()
    val event = _event.asSharedFlow()

    init {
        loadDetail()
    }

    fun onAction(action: DetailAction) {
        when (action) {
            is DetailAction.OnBackClick -> {
                viewModelScope.launch {
                    _event.emit(DetailEvent.NavigateBack)
                }
            }
            is DetailAction.OnCallClick -> {
                val tel = _state.value.stationDetail?.tel ?: return
                viewModelScope.launch {
                    _event.emit(DetailEvent.MakeCall(tel))
                }
            }
            is DetailAction.OnNavigateClick -> {
                val address = _state.value.stationDetail?.address ?: return
                viewModelScope.launch {
                    _event.emit(DetailEvent.OpenNavigation(address))
                }
            }
            is DetailAction.OnRefreshClick -> {
                loadDetail()
            }
            is DetailAction.OnFavoriteToggle -> {
                val detail = _state.value.stationDetail ?: return
                viewModelScope.launch {
                    val station = Station(
                        id = detail.id,
                        name = detail.name,
                        brandCode = detail.brandCode,
                        price = detail.gasolinePrice,
                        isFavorite = detail.isFavorite
                    )
                    toggleFavoriteUseCase(station)
                    _state.update {
                        it.copy(stationDetail = detail.copy(isFavorite = !detail.isFavorite))
                    }
                }
            }
        }
    }

    private fun loadDetail() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val location = locationTracker.getCurrentLocation()
            val katecCoords = location?.let {
                LocationUtil.wgs84ToKatec(it.latitude, it.longitude)
            }

            when (val result = getStationDetailUseCase(
                stationId = stationId,
                userKatecX = katecCoords?.first,
                userKatecY = katecCoords?.second
            )) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            stationDetail = result.data
                        )
                    }
                }
                is Result.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = "상세 정보를 불러올 수 없습니다."
                        )
                    }
                }
            }
        }
    }
}
