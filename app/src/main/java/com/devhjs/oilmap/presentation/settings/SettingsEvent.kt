package com.devhjs.oilmap.presentation.settings

sealed interface SettingsEvent {
    data object NavigateBack : SettingsEvent
}
