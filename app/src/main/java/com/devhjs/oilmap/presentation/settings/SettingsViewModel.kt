package com.devhjs.oilmap.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhjs.oilmap.domain.model.OilType
import com.devhjs.oilmap.domain.model.SortType
import com.devhjs.oilmap.domain.usecase.GetUserPreferenceUseCase
import com.devhjs.oilmap.domain.usecase.SaveUserPreferenceUseCase
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
class SettingsViewModel @Inject constructor(
    private val getUserPreferenceUseCase: GetUserPreferenceUseCase,
    private val saveUserPreferenceUseCase: SaveUserPreferenceUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<SettingsEvent>()
    val event = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val prefs = getUserPreferenceUseCase().first()
            _state.update {
                it.copy(
                    favoriteOilType = prefs.favoriteOilType,
                    defaultSortType = prefs.defaultSortType,
                    searchRadius = prefs.searchRadius,
                    isLoading = false
                )
            }
        }
    }

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.OnFavoriteOilTypeChanged -> {
                _state.update { it.copy(favoriteOilType = action.oilType) }
                viewModelScope.launch { saveUserPreferenceUseCase.updateFavoriteOilType(action.oilType) }
            }
            is SettingsAction.OnDefaultSortTypeChanged -> {
                _state.update { it.copy(defaultSortType = action.sortType) }
                viewModelScope.launch { saveUserPreferenceUseCase.updateDefaultSortType(action.sortType) }
            }
            is SettingsAction.OnSearchRadiusChanged -> {
                _state.update { it.copy(searchRadius = action.radius) }
                viewModelScope.launch { saveUserPreferenceUseCase.updateSearchRadius(action.radius) }
            }
            is SettingsAction.OnResetPreferences -> {
                _state.update {
                    it.copy(
                        favoriteOilType = OilType.GASOLINE,
                        defaultSortType = SortType.DISTANCE,
                        searchRadius = 3000
                    )
                }
                viewModelScope.launch {
                    saveUserPreferenceUseCase.updateFavoriteOilType(OilType.GASOLINE)
                    saveUserPreferenceUseCase.updateDefaultSortType(SortType.DISTANCE)
                    saveUserPreferenceUseCase.updateSearchRadius(3000)
                }
            }
            is SettingsAction.OnBackClick -> {
                viewModelScope.launch {
                    _event.emit(SettingsEvent.NavigateBack)
                }
            }
        }
    }
}
