package com.ganaljigi.kubf.dummy.ui.dummy.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ganaljigi.kubf.dummy.data.repository.DummyRepository
import com.ganaljigi.kubf.dummy.mapper.toUiState
import com.ganaljigi.kubf.dummy.ui.dummy.model.DummyUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DummyViewModel @Inject constructor(
    private val dummyRepository: DummyRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<DummyUiState> = MutableStateFlow(DummyUiState())
    val uiState: StateFlow<DummyUiState> = _uiState
        .onStart {
            loadDummyData()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DummyUiState()
        )


    private fun loadDummyData() {
        viewModelScope.launch {
            dummyRepository.dummy().fold(
                onSuccess = { data ->
                    _uiState.value = data.toUiState()
                },
                onFailure = { error ->
                    Log.e("DummyViewModel", "Error loading dummy data: ${error.message}")
                }
            )
        }
    }
}