package com.ganaljigi.kubf.ui.buildinginfo.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class BuildingViewModel:ViewModel(){
    private val _uiState: MutableStateFlow<BuildingUIState> = MutableStateFlow(BuildingUIState())
    val uiState = _uiState.asStateFlow()
}