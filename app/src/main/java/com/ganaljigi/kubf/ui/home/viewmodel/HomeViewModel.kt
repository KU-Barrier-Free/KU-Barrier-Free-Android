package com.ganaljigi.kubf.ui.home.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ganaljigi.kubf.ui.common.model.MapMarker
import com.ganaljigi.kubf.ui.common.model.MapToggle
import com.ganaljigi.kubf.ui.common.model.SearchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState = _uiState
        .onStart { fetchInitData() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState()
        )

    fun updateSearchWord(newSearchWord: TextFieldValue = TextFieldValue("")) {
        _uiState.update { it.copy(searchWord = newSearchWord) }
    }

    fun updateBuildingInfo(buildingInfo: HomeBuildingInfo?) {
        _uiState.update { it.copy(buildingInfo = buildingInfo) }
    }

    fun updateSearchResults(newSearchResults: List<SearchResult>) {
        _uiState.update { it.copy(searchResults = persistentListOf(*newSearchResults.toTypedArray())) }
    }

    fun setBarrierFreeShown(isBarrierFreeShown: Boolean) {
        _uiState.update { it.copy(isBarrierFreeShown = isBarrierFreeShown) }
    }

    fun setFindMode(isFindMode: Boolean) {
        _uiState.update { it.copy(isFindMode = isFindMode) }
    }

    fun setShowSearchBottomSheet(showSearchBottomSheet: Boolean) {
        _uiState.update { it.copy(showSearchBottomSheet = showSearchBottomSheet) }
    }

    fun setShowBuildingInfoBottomSheet(showBuildingInfoBottomSheet: Boolean) {
        _uiState.update { it.copy(showBuildingInfoBottomSheet = showBuildingInfoBottomSheet) }
    }

    fun updateToggleUiStates(toggle: MapToggle) {
        _uiState.update {
            val updatedToggles = it.toggleUiStates.map { toggleUiState ->
                if (toggleUiState.toggle == toggle) {
                    toggleUiState.copy(isSelected = !toggleUiState.isSelected)
                } else {
                    toggleUiState
                }
            }
            it.copy(toggleUiStates = updatedToggles)
        }
    }

    private fun fetchInitData() {
        _uiState.value = HomeUiState(
            buildingMarkers = persistentListOf(
                MapMarker(
                    id = 1L,
                    name = "경영관",
                    latitude = 37.544338,
                    longitude = 127.076273,
                ),
                MapMarker(
                    id = 2L,
                    name = "새천년관",
                    latitude = 37.543496,
                    longitude = 127.077326,
                )
            ),
            curbMarkers = persistentListOf(
                MapMarker(
                    id = 1L,
                    latitude = 37.543644,
                    longitude = 127.076553,
                ),
                MapMarker(
                    id = 2L,
                    latitude = 37.543352,
                    longitude = 127.076816,
                )
            ),
            slopeMarkers = persistentListOf(
                MapMarker(
                    id = 1L,
                    latitude = 37.543333,
                    longitude = 127.076627,
                ),
                MapMarker(
                    id = 2L,
                    latitude = 37.543944,
                    longitude = 127.077185,
                )
            ),
            stairsMarkers = persistentListOf(
                MapMarker(
                    id = 1L,
                    latitude = 37.543259,
                    longitude = 127.075662,
                ),
                MapMarker(
                    id = 2L,
                    latitude = 37.543611,
                    longitude = 127.075206,
                )
            ),
            specialMarkers = persistentListOf(
                MapMarker(
                    id = 1L,
                    latitude = 37.543141,
                    longitude = 127.076135,
                ),
                MapMarker(
                    id = 2L,
                    latitude = 37.543010,
                    longitude = 127.078079,
                )
            ),
        )
    }
}