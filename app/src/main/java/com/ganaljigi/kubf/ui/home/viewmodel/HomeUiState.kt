package com.ganaljigi.kubf.ui.home.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.ganaljigi.kubf.ui.common.model.Convenience
import com.ganaljigi.kubf.ui.common.model.DoorInfo
import com.ganaljigi.kubf.ui.home.model.BuildingMarker
import com.ganaljigi.kubf.ui.home.model.DoorMarker
import com.ganaljigi.kubf.ui.home.model.MapToggle
import com.ganaljigi.kubf.ui.home.model.SearchResult
import com.ganaljigi.kubf.ui.home.model.ToggleMarker
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class HomeUiState(
    val cameraPositionState: CameraPositionState = CameraPositionState(
        position = CameraPosition.fromLatLngZoom(LatLng(37.5407, 127.0785), 16f)
    ),
    val searchWord: TextFieldValue = TextFieldValue(""),
    val buildingInfo: HomeBuildingInfo = HomeBuildingInfo(),
    val searchResults: ImmutableList<SearchResult> = persistentListOf(),
    val isBarrierFreeShown: Boolean = false,
    val isFindMode: Boolean = false,
    val bottomSheetType: HomeBottomSheetType = HomeBottomSheetType.NONE,
    val showInquiryDialog: Boolean = false,
    val inquiryField: TextFieldValue = TextFieldValue(""),
    val toggleUiStates: List<ToggleUiState> = MapToggle.entries.map {
        ToggleUiState(
            isSelected = it == MapToggle.SPECIAL_MARK,
            toggle = it
        )
    },
    val buildingMarkers: ImmutableList<BuildingMarker> = persistentListOf(),
    val selectedBuildingMarker: BuildingMarker? = null,
    val doorMarkers: ImmutableList<DoorMarker> = persistentListOf(),
    val curbMarkers: ImmutableList<ToggleMarker> = persistentListOf(),
    val slopeMarkers: ImmutableList<ToggleMarker> = persistentListOf(),
    val stairsMarkers: ImmutableList<ToggleMarker> = persistentListOf(),
    val specialMarkers: ImmutableList<ToggleMarker> = persistentListOf(),
    val showingToggleMarkers: ImmutableList<ToggleMarker> = persistentListOf(),
    val selectedSpecialMarker: ToggleMarker? = null,
    val specialMarkerInfo: SpecialMarkerInfo? = null,
    val popularKeywords: ImmutableList<String> = persistentListOf(),
)

enum class HomeBottomSheetType {
    NONE,
    SEARCH,
    BUILDING_INFO,
}

data class HomeBuildingInfo(
    val id: Long = 0L,
    val name: String = "",
    val buildingNumber: Int = 0,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val convenienceList: ImmutableList<Convenience> = persistentListOf(),
    val doorInfoList: ImmutableList<DoorInfo> = persistentListOf(),
)

data class SpecialMarkerInfo(
    val id: Long,
    val markerId: Long,
    val imageUrl: String,
    val description: String,
)

data class ToggleUiState(
    val toggle: MapToggle = MapToggle.CURB,
    val isSelected: Boolean = false
)