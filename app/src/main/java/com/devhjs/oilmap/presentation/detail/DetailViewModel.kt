package com.devhjs.oilmap.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.oilmap.core.util.Result
import com.devhjs.oilmap.domain.usecase.GetStationDetailUseCase
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
                // 추후 즐겨찾기 토글 로직 구현
            }
        }
    }

    private fun loadDetail() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            when (val result = getStationDetailUseCase(stationId)) {
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
