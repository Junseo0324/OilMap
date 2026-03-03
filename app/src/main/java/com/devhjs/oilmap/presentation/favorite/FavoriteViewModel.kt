package com.devhjs.oilmap.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.oilmap.domain.usecase.GetFavoriteStationsUseCase
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
class FavoriteViewModel @Inject constructor(
    private val getFavoriteStationsUseCase: GetFavoriteStationsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FavoriteState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<FavoriteEvent>()
    val event = _event.asSharedFlow()

    init {
        observeFavorites()
    }

    fun onAction(action: FavoriteAction) {
        when (action) {
            is FavoriteAction.OnStationClick -> {
                viewModelScope.launch {
                    _event.emit(FavoriteEvent.NavigateToStationDetail(action.stationId))
                }
            }
            is FavoriteAction.OnResourceTypeSelected -> {
                _state.update { it.copy(selectedResourceType = action.resourceType) }
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

    private fun observeFavorites() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            getFavoriteStationsUseCase().collect { stations ->
                val uiModels = stations.map { station ->
                    FavoriteStationUiModel(
                        station = station
                    )
                }
                _state.update {
                    it.copy(
                        stations = uiModels,
                        totalCount = uiModels.size,
                        isLoading = false
                    )
                }
            }
        }
    }
}
