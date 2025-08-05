package com.ganaljigi.kubf.ui.roominfo.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class RoomInfoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RoomInfoUiState())

    val uiState = _uiState.asStateFlow()
}