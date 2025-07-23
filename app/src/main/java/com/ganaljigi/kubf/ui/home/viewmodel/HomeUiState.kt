package com.ganaljigi.kubf.ui.home.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.ganaljigi.kubf.ui.common.model.MapMarker
import com.ganaljigi.kubf.ui.common.model.MapToggle
import com.ganaljigi.kubf.ui.common.model.SearchResult
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class HomeUiState(
    val searchWord: TextFieldValue = TextFieldValue(""),
    val buildingInfo: HomeBuildingInfo? = null,
    val searchResults: ImmutableList<SearchResult> = persistentListOf(),
    val isBarrierFreeShown: Boolean = false,
    val isFindMode: Boolean = false,
    val showSearchBottomSheet: Boolean = false,
    val showBuildingInfoBottomSheet: Boolean = false,
    val showInquiryDialog: Boolean = false,
    val inquiryField: TextFieldValue = TextFieldValue(""),
    val toggleUiStates: List<ToggleUiState> = MapToggle.entries.map {
        ToggleUiState(
            isSelected = it == MapToggle.SPECIAL_MARK,
            toggle = it
        )
    },
    val buildingMarkers: ImmutableList<MapMarker> = persistentListOf(),
    val doorMarkers: ImmutableList<MapMarker> = persistentListOf(),
    val curbMarkers: ImmutableList<MapMarker> = persistentListOf(),
    val slopeMarkers: ImmutableList<MapMarker> = persistentListOf(),
    val stairsMarkers: ImmutableList<MapMarker> = persistentListOf(),
    val specialMarkers: ImmutableList<MapMarker> = persistentListOf(),
    val specialMarkerInfo: SpecialMarkerInfo? = null,
    val chosenBuildingPin: MapMarker? = null,
    val popularKeywords: ImmutableList<String> = persistentListOf(),
//    val showingMarkers: ImmutableList<MapMarker> = persistentListOf(),
)

data class HomeBuildingInfo(
    val id: Long,
    val name: String,
    val buildingNumber: Int,
    val latitude: Double,
    val longitude: Double,
    // TODO: 편의시설, 출입문 모델 설정
)

data class SpecialMarkerInfo(
    val id: Long,
    val imageUrl: String,
    val description: String,
    val latitude: Double,
    val longitude: Double
)

data class ToggleUiState(
    val toggle: MapToggle = MapToggle.CURB,
    val isSelected: Boolean = false
)