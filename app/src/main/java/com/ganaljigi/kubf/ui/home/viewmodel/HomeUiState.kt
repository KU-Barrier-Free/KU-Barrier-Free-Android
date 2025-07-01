package com.ganaljigi.kubf.ui.home.viewmodel

import com.ganaljigi.kubf.ui.common.model.MapToggle

//data class HomeUiState()


data class ToggleUiState(
    val toggle: MapToggle = MapToggle.CURB,
    val isSelected: Boolean = false
)