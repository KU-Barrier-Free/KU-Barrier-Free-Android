package com.ganaljigi.kubf.ui.home.viewmodel

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ganaljigi.kubf.ui.common.model.MapMarker
import com.ganaljigi.kubf.ui.common.model.MapToggle
import com.ganaljigi.kubf.ui.common.model.SearchResult
import com.ganaljigi.kubf.ui.theme.MainGreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
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
//        getSearchResults()
    }

    fun updateInquiryField(newInquiryField: TextFieldValue) {
        _uiState.update { it.copy(inquiryField = newInquiryField) }
    }

    fun submitInquiry() {
        // TODO: 문의 API 호출
        setShowInquiryDialog(false)
    }

    fun getSearchResults() {
        // TODO: 검색 API 호출
        _uiState.update { // 임시
            it.copy(
                searchResults = persistentListOf(
                    SearchResult(
                        id = 1,
                        isBuilding = true,
                        name = "경영관",
                        building = "경영관",
                        annotatedName = buildAnnotatedString { }
                    ),
                    SearchResult(
                        id = 2,
                        name = "카페 레스티오",
                        building = "경영관",
                        annotatedName = buildAnnotatedString {
                            append("카페 ")
                            withStyle(
                                style = SpanStyle(
                                    color = MainGreen,
                                ),
                            ) {
                                append("레스티")
                            }
                            append("오")
                        }
                    ),
                    SearchResult(
                        id = 3,
                        name = "카페 레스티오",
                        building = "공학관",
                        annotatedName = buildAnnotatedString {
                            append("카페 ")
                            withStyle(
                                style = SpanStyle(
                                    color = MainGreen,
                                ),
                            ) {
                                append("레스티")
                            }
                            append("오")
                        }
                    )
                )
            )
        }
    }

    fun updateBuildingInfo(buildingInfo: HomeBuildingInfo) {
        // TODO: 건물 정보 API 호출
        _uiState.update { it.copy(buildingInfo = buildingInfo) }
    }

    fun updateSearchResults(newSearchResults: List<SearchResult> = uiState.value.searchResults) {
        _uiState.update {
            it.copy(
                showSearchBottomSheet = true,
                searchResults = newSearchResults.toImmutableList()
            )
        }
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

    fun setShowInquiryDialog(showInquiryDialog: Boolean) {
        val newInquiryField =
            uiState.value.inquiryField.takeIf { !showInquiryDialog } ?: TextFieldValue("")
        _uiState.update {
            it.copy(
                showInquiryDialog = showInquiryDialog,
                inquiryField = newInquiryField,
            )
        }
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