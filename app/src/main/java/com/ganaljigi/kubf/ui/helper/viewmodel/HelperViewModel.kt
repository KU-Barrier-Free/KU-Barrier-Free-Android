package com.ganaljigi.kubf.ui.helper.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ganaljigi.kubf.ui.helper.mapper.toUiState
import com.ganaljigi.kubf.ui.helper.repository.HelperRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HelperViewModel @Inject constructor(
    private val repository: HelperRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(HelperUiState())
    val uiState: StateFlow<HelperUiState> = _uiState
        .onStart { loadNotices() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HelperUiState()
        )

    fun loadNotices(

    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            repository.fetchNotices().fold(
                onSuccess = { dto ->
                    val mapped = dto.toUiState()
                    _uiState.value = mapped.copy(isLoading = false)
                },
                onFailure = { e ->
                     _uiState.update { it.copy(isLoading = false, error = e.message ?: "") }
                }
            )
        }
    }
    fun retry() = loadNotices()
}