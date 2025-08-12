package com.ganaljigi.kubf.ui.home.viewmodel

import android.util.Log
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ganaljigi.kubf.data.remote.repository.HomeRepository
import com.ganaljigi.kubf.mapper.toSpecialMarkerInfo
import com.ganaljigi.kubf.mapper.toUiState
import com.ganaljigi.kubf.ui.common.model.Convenience
import com.ganaljigi.kubf.ui.common.model.DoorInfo
import com.ganaljigi.kubf.ui.common.model.RouteMode
import com.ganaljigi.kubf.ui.home.model.BuildingMarker
import com.ganaljigi.kubf.ui.home.model.MapToggle
import com.ganaljigi.kubf.ui.home.model.RouteResult
import com.ganaljigi.kubf.ui.home.model.SearchResult
import com.ganaljigi.kubf.ui.home.model.ToggleMarker
import com.ganaljigi.kubf.ui.theme.MainGreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchInitData()
    }

    fun updateSearchWord(newSearchWord: TextFieldValue = TextFieldValue("")) {
        _uiState.update { it.copy(searchWord = newSearchWord) }
        getSearchResults()
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
        val searchWord = uiState.value.searchWord.text
        viewModelScope.launch {
            homeRepository.getHomeSearchResult(searchWord).fold(
                onSuccess = { response ->
                    updateSearchResults(response.toUiState(searchWord))
                },
                onFailure = { error ->
                    Log.e("HomeViewModel", "getSearchResults: Error fetching search results", error)
                }
            )
        }

        _uiState.update { // 임시
            it.copy(
                searchResults = persistentListOf(
                    SearchResult(
                        id = 1,
                        isBuilding = true,
                        name = "경영관",
                        building = "경영관",
                        searchKeyword = "경영관"
                    ),
                    SearchResult(
                        id = 2,
                        name = "카페 레스티오",
                        building = "경영관",
                        searchKeyword = "경영관"
                    ),
                    SearchResult(
                        id = 3,
                        name = "카페 레스티오",
                        building = "공학관",
                        searchKeyword = "경영관"
                    )
                )
            )
        }
    }

    fun getSpecialMarkerInfo(selectedSpecialMarker: ToggleMarker) {
        // TODO: 특이사항 정보 API 호출
        updateSelectedSpecialMarker(selectedSpecialMarker)
        updateSpecialMarkerInfo(selectedSpecialMarker)
    }

    fun updateBuildingInfo(buildingInfo: HomeBuildingInfo) {
        // TODO: 건물 정보 API 호출
        viewModelScope.launch {

        }
        _uiState.update { it.copy(buildingInfo = buildingInfo) }
    }

    fun updateSearchResults(
        newSearchResults: List<SearchResult> = uiState.value.searchResults
    ) {
        if (newSearchResults.size == 1) {
            setSingleResult(newSearchResults.first())
        } else {
            _uiState.update {
                it.copy(
                    selectedBuildingMarker = null,
                    bottomSheetType = HomeBottomSheetType.SEARCH,
                    searchResults = newSearchResults.toImmutableList()
                )
            }
        }
    }

    private fun setSingleResult(searchResult: SearchResult) {
        if (searchResult.isBuilding) {
            getBuildingInfoByResult(searchResult)
        } else {
            _uiState.update {
                it.copy(
                    selectedBuildingMarker = null,
                    bottomSheetType = HomeBottomSheetType.SEARCH,
                    searchResults = persistentListOf(searchResult),
                )
            }
        }
    }


    fun onFromClick(searchResult: SearchResult) {
        updateFromLocation(searchResult)
        setHomeUiMode(HomeUiMode.FIND_MODE)
        setBottomSheetType(HomeBottomSheetType.NONE)
    }

    fun onToClick(searchResult: SearchResult) {
        updateToLocation(searchResult)
        setHomeUiMode(HomeUiMode.FIND_MODE)
        setBottomSheetType(HomeBottomSheetType.NONE)
    }

    fun updateFromLocation(fromLocation: SearchResult) {
        _uiState.update {
            it.copy(
                searchWord = TextFieldValue(""),
                fromLocation = fromLocation
            )
        }
        if (fromLocation.name.isNotEmpty() && uiState.value.toLocation.name.isNotEmpty()) {
            getRouteBetweenLocations()
        }
    }

    fun updateToLocation(toLocation: SearchResult) {
        _uiState.update {
            it.copy(
                searchWord = TextFieldValue(""),
                toLocation = toLocation
            )
        }
        if (toLocation.name.isNotEmpty() && uiState.value.fromLocation.name.isNotEmpty()) {
            getRouteBetweenLocations()
        }
    }

    fun changeFromToLocation() {
        _uiState.update {
            it.copy(
                fromLocation = it.toLocation,
                toLocation = it.fromLocation,
            )
        }
    }

    private fun getRouteBetweenLocations() {
        // TODO: 경로 API 호출
        Log.d(
            "HomeViewModel",
            "getRouteBetweenLocations: from=${uiState.value.fromLocation.name}, to=${uiState.value.toLocation.name}"
        )
        _uiState.update {
            it.copy(
                homeUiMode = HomeUiMode.ROUTE_MODE,
                routeResults = persistentListOf(
                    RouteResult(routeMode = RouteMode.SHORTEST, time = 7, distance = 428),
                    RouteResult(routeMode = RouteMode.BARRIER_FREE, time = 14, distance = 1136),
                )
            )
        }
    }

    private fun getBuildingInfoByResult(searchResult: SearchResult) {
        // TODO: 건물 정보 API 호출
        updateBuildingInfo(
            HomeBuildingInfo(
                id = 1L,
                name = "경영관",
                buildingNumber = 1,
                latitude = 0.0,
                longitude = 0.0,
                convenienceList = Convenience.entries.toImmutableList(),
                doorInfoList = persistentListOf(
                    DoorInfo(
                        label = "B",
                        imageUrl = "",
                        description = "입구 설명",
                        isWheelchairAccessible = true
                    ),
                    DoorInfo(
                        label = "B",
                        imageUrl = "",
                        description = "입구 설명",
                        isWheelchairAccessible = true
                    ),
                )
            )
        )
        _uiState.update {
            it.copy(
                bottomSheetType = HomeBottomSheetType.BUILDING_INFO,
            )
        }
    }

    fun getBuildingInfoByMarker(selectedBuildingMarker: BuildingMarker) {
        // TODO: 건물 정보 API 호출
        updateBuildingInfo(
            if (selectedBuildingMarker.id == 1L) {
                HomeBuildingInfo(
                    id = 1L,
                    name = "경영관",
                    buildingNumber = 1,
                    latitude = selectedBuildingMarker.latitude,
                    longitude = selectedBuildingMarker.longitude,
                    convenienceList = Convenience.entries.toImmutableList(),
                    doorInfoList = persistentListOf(
                        DoorInfo(
                            label = "B",
                            imageUrl = "",
                            description = "입구 설명",
                            isWheelchairAccessible = true
                        ),
                        DoorInfo(
                            label = "B",
                            imageUrl = "",
                            description = "입구 설명",
                            isWheelchairAccessible = true
                        ),
                    )
                )
            } else {
                HomeBuildingInfo(
                    id = 2L,
                    name = "새천년관",
                    buildingNumber = 2,
                    latitude = selectedBuildingMarker.latitude,
                    longitude = selectedBuildingMarker.longitude,
                    convenienceList = Convenience.entries.take(4).toImmutableList(),
                    doorInfoList = persistentListOf(
                        DoorInfo(
                            label = "B",
                            imageUrl = "",
                            description = "입구 설명",
                            isWheelchairAccessible = true
                        ),
                        DoorInfo(
                            label = "B",
                            imageUrl = "",
                            description = "입구 설명",
                            isWheelchairAccessible = true
                        ),
                    )
                )
            }
        )
        updateSelectedBuildingMarker(selectedBuildingMarker)
    }

    private fun updateSelectedBuildingMarker(selectedBuildingMarker: BuildingMarker) {
        _uiState.update {
            it.copy(
                selectedBuildingMarker = selectedBuildingMarker,
                selectedSpecialMarker = null,
                bottomSheetType = HomeBottomSheetType.BUILDING_INFO,
                searchResults = persistentListOf(),
            )
        }
    }

    private fun updateSelectedSpecialMarker(selectedSpecialMarker: ToggleMarker) {
        _uiState.update {
            it.copy(
                selectedBuildingMarker = null,
                selectedSpecialMarker = selectedSpecialMarker,
                bottomSheetType = HomeBottomSheetType.NONE,
                searchResults = persistentListOf(),
            )
        }
    }

    fun setHomeUiMode(homeUiMode: HomeUiMode) {
        _uiState.update { it.copy(homeUiMode = homeUiMode) }
    }

    fun setBottomSheetType(bottomSheetType: HomeBottomSheetType) {
        _uiState.update { it.copy(bottomSheetType = bottomSheetType) }
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

    fun setShowSpecialImageDialog(
        showSpecialImageDialog: Boolean,
        imageUrl: List<String> = emptyList(),
    ) {
        _uiState.update {
            it.copy(
                specialImageUrl = imageUrl.toImmutableList(),
                showSpecialImageDialog = showSpecialImageDialog,
                selectedSpecialMarker =
                    if (showSpecialImageDialog) it.selectedSpecialMarker else null,
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
            val newShowingToggleMarkers = it.toggleUiStates
                .filter { toggleUiState -> toggleUiState.isSelected }
                .map { toggleUiState ->
                    when (toggleUiState.toggle) {
                        MapToggle.CURB -> uiState.value.curbMarkers
                        MapToggle.SLOPE -> uiState.value.slopeMarkers
                        MapToggle.STAIRS -> uiState.value.stairsMarkers
                        MapToggle.SPECIAL_MARK -> uiState.value.specialMarkers
                    }
                }.flatten().toImmutableList()
            it.copy(
                homeUiMode = HomeUiMode.DEFAULT,
                toggleUiStates = updatedToggles,
                showingToggleMarkers = newShowingToggleMarkers,
            )
        }

        val newShowingToggleMarkers = uiState.value.toggleUiStates
            .filter { it.isSelected }
            .map { toggleUiState ->
                when (toggleUiState.toggle) {
                    MapToggle.CURB -> uiState.value.curbMarkers
                    MapToggle.SLOPE -> uiState.value.slopeMarkers
                    MapToggle.STAIRS -> uiState.value.stairsMarkers
                    MapToggle.SPECIAL_MARK -> uiState.value.specialMarkers
                }
            }.flatten().toImmutableList()
        _uiState.update { it.copy(showingToggleMarkers = newShowingToggleMarkers) }
    }

    fun updateSpecialMarkerInfo(toggleMarker: ToggleMarker) {
        viewModelScope.launch {
            homeRepository.getSpecialInfo(toggleMarker.id).fold(
                onSuccess = { response ->
                    _uiState.update {
                        it.copy(
                            specialMarkerInfo = response.toSpecialMarkerInfo(),
//                                SpecialMarkerInfo(
//                                imageUrls = listOf(
//                                    "https://cdn.pixabay.com/photo/2015/07/08/01/22/korean-jindo-835301_1280.jpg",
//                                    "https://cdn.pixabay.com/photo/2015/07/08/01/22/korean-jindo-835301_1280.jpg"
//                                ),
//                                description = response.toSpecialMarkerInfo().description
                        )
                    }
                },
                onFailure = { error ->
                    Log.e(
                        "HomeViewModel",
                        "updateSpecialMarkerInfo: Error fetching special info",
                        error
                    )
                }
            )
        }

        _uiState.update {
            it.copy(
                specialMarkerInfo = SpecialMarkerInfo(
                    imageUrls = listOf(
                        "https://cdn.pixabay.com/photo/2015/07/08/01/22/korean-jindo-835301_1280.jpg",
                        "https://cdn.pixabay.com/photo/2015/07/08/01/22/korean-jindo-835301_1280.jpg"
                    ),
                    description = "사진 기준 왼쪽에 경사로가 있어서\n장애 학우들도 이용 가능합니다."
                )
            )
        }
    }

    private fun fetchInitData() {
        viewModelScope.launch {
            homeRepository.getHomeData().fold(
                onSuccess = { response ->
                    _uiState.value = response.toUiState()
                },
                onFailure = { error ->
                    Log.e("HomeViewModel", "fetchInitData: Error fetching home data", error)
                }
            )
        }

        val curbMarkers = persistentListOf(
            ToggleMarker(
                id = 1L,
                latitude = 37.543644,
                longitude = 127.076553,
                mapToggle = MapToggle.CURB,
            ),
            ToggleMarker(
                id = 2L,
                latitude = 37.543352,
                longitude = 127.076816,
                mapToggle = MapToggle.CURB,
            )
        )
        val slopeMarkers = persistentListOf(
            ToggleMarker(
                id = 3L,
                latitude = 37.543333,
                longitude = 127.076627,
                mapToggle = MapToggle.SLOPE,
            ),
            ToggleMarker(
                id = 4L,
                latitude = 37.543944,
                longitude = 127.077185,
                mapToggle = MapToggle.SLOPE,
            )
        )
        val stairsMarkers = persistentListOf(
            ToggleMarker(
                id = 5L,
                latitude = 37.543259,
                longitude = 127.075662,
                mapToggle = MapToggle.STAIRS,
            ),
            ToggleMarker(
                id = 6L,
                latitude = 37.543611,
                longitude = 127.075206,
                mapToggle = MapToggle.STAIRS,
            )
        )
        val specialMarkers = persistentListOf(
            ToggleMarker(
                id = 7L,
                latitude = 37.543141,
                longitude = 127.076135,
                mapToggle = MapToggle.SPECIAL_MARK,
            ),
            ToggleMarker(
                id = 8L,
                latitude = 37.543010,
                longitude = 127.078079,
                mapToggle = MapToggle.SPECIAL_MARK,
            )
        )
        _uiState.value = HomeUiState(
            buildingMarkers = persistentListOf(
                BuildingMarker(
                    id = 9L,
                    name = "경영관",
                    latitude = 37.544338,
                    longitude = 127.076273,
                ),
                BuildingMarker(
                    id = 10L,
                    name = "새천년관",
                    latitude = 37.543496,
                    longitude = 127.077326,
                ),
            ),
            curbMarkers = curbMarkers,
            slopeMarkers = slopeMarkers,
            stairsMarkers = stairsMarkers,
            specialMarkers = specialMarkers,
            showingToggleMarkers = specialMarkers
        )
    }

    fun selectRoute(routeResult: RouteResult) {
        _uiState.update {
            it.copy(
                selectedRouteResult = routeResult,
            )
        }
    }

    fun setDefaultMode() {
        _uiState.update {
            it.copy(
                homeUiMode = HomeUiMode.DEFAULT,
                bottomSheetType = HomeBottomSheetType.NONE,
                showInquiryDialog = false,
                selectedBuildingMarker = null,
                selectedSpecialMarker = null,
                selectedRouteResult = RouteResult(),
                fromLocation = SearchResult(),
                toLocation = SearchResult(),
                searchResults = persistentListOf(),
            )
        }
    }
}