package com.ganaljigi.kubf.ui.home.viewmodel

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ganaljigi.kubf.data.remote.repository.BuildingRepository
import com.ganaljigi.kubf.data.remote.repository.HomeRepository
import com.ganaljigi.kubf.data.remote.repository.RouteRepository
import com.ganaljigi.kubf.mapper.toHomeBuildingInfo
import com.ganaljigi.kubf.mapper.toDoorMarkers
import com.ganaljigi.kubf.mapper.toRouteResults
import com.ganaljigi.kubf.mapper.toSpecialMarkerInfo
import com.ganaljigi.kubf.mapper.toUiState
import com.ganaljigi.kubf.ui.home.model.BuildingMarker
import com.ganaljigi.kubf.ui.home.model.MapToggle
import com.ganaljigi.kubf.ui.home.model.RouteResult
import com.ganaljigi.kubf.ui.home.model.SearchResult
import com.ganaljigi.kubf.ui.home.model.ToggleMarker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.flatten

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val buildingRepository: BuildingRepository,
    private val routeRepository: RouteRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchInitData()
    }

    fun updateSearchWord(newSearchWord: TextFieldValue = TextFieldValue("")) {
        Log.d("HomeViewModel", "updateSearchWord: $newSearchWord")
        if (newSearchWord.text == uiState.value.searchWord.text) return
        _uiState.update { it.copy(searchWord = newSearchWord) }
        getSearchResults(newSearchWord.text)
    }

    fun updateInquiryField(newInquiryField: TextFieldValue) {
        _uiState.update { it.copy(inquiryField = newInquiryField) }
    }

    fun submitInquiry() {
        // TODO: 문의 API 호출
        setShowInquiryDialog(false)
    }

    fun getSearchResults(newSearchWord: String = uiState.value.searchWord.text) {
        if (newSearchWord.isEmpty()) {
            return
        }
        viewModelScope.launch {
            homeRepository.getHomeSearchResult(newSearchWord).fold(
                onSuccess = { response ->
                    updateSearchResults(response.toUiState(newSearchWord), false)
                },
                onFailure = { error ->
                    Log.e("HomeViewModel", "getSearchResults: Error fetching search results", error)
                }
            )
        }

    }

    fun getSpecialMarkerInfo(selectedSpecialMarker: ToggleMarker) {
        // TODO: 특이사항 정보 API 호출
        updateSelectedSpecialMarker(selectedSpecialMarker)
        updateSpecialMarkerInfo(selectedSpecialMarker)
    }

    fun updateBuildingInfo(
        buildingId: Long = 1L,
    ) {


        // TODO: 건물 정보 API 호출
        viewModelScope.launch {
            buildingRepository.getBuildingInfo(buildingId = buildingId)
                .onSuccess { response ->
                    _uiState.update {
                        it.copy(
                            buildingInfo = response.toHomeBuildingInfo(),
                            showingDoorMarkers = response.toDoorMarkers().toImmutableList()
                        )
                    }
                }
                .onFailure { error ->
                    Log.e(
                        "HomeViewModel",
                        "updateBuildingInfo: Error fetching building info",
                        error
                    )
                }

        }
    }

    fun updateSearchResults(
        newSearchResults: List<SearchResult> = uiState.value.searchResults,
        showSheet: Boolean = true,
    ) {
        Log.d("HomeViewModel", "updateSearchResults: $newSearchResults")
        if (newSearchResults.size == 1) {
            setSingleResult(newSearchResults.first())
        } else {
            _uiState.update {
                it.copy(
                    selectedBuildingMarker = null,
                    searchResults = newSearchResults.toImmutableList(),
                )
            }
            if (showSheet) {
                setBottomSheetType(HomeBottomSheetType.SEARCH)
            }
        }
    }

    private fun setSingleResult(searchResult: SearchResult) {
        Log.d("HomeViewModel", "setSingleResult: $searchResult")
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
        setHomeUiMode(HomeUiMode.FIND_MODE)
        setBottomSheetType(HomeBottomSheetType.NONE)
        updateFromLocation(searchResult)
    }

    fun onToClick(searchResult: SearchResult) {
        setHomeUiMode(HomeUiMode.FIND_MODE)
        setBottomSheetType(HomeBottomSheetType.NONE)
        updateToLocation(searchResult)
    }

    fun updateFromLocation(fromLocation: SearchResult) {
        _uiState.update {
            it.copy(
                searchWord = TextFieldValue(""),
                searchResults = persistentListOf(),
                fromLocation = fromLocation,
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
                searchResults = persistentListOf(),
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

        // 바뀐 출발지와 도착지로 경로 다시 검색
        val fromLocation = uiState.value.fromLocation
        val toLocation = uiState.value.toLocation
        if (fromLocation.name.isNotEmpty() && toLocation.name.isNotEmpty()) {
            getRouteBetweenLocations()
        }
    }

    private fun getRouteBetweenLocations() {
        val fromLocation = _uiState.value.fromLocation
        val toLocation = _uiState.value.toLocation

        Log.d(
            "HomeViewModel",
            "getRouteBetweenLocations: from=${fromLocation.name}, to=${toLocation.name}"
        )

        viewModelScope.launch {
            routeRepository.getPath(
                srcId = fromLocation.id,
                srcType = if (fromLocation.isBuilding) "BUILDING" else "FACILITY",
                destId = toLocation.id,
                destType = if (toLocation.isBuilding) "BUILDING" else "FACILITY"
            ).fold(
                onSuccess = { response ->
                    val routeResults = response.toRouteResults()
                    if (routeResults.isNotEmpty()) {
                        _uiState.update {
                            it.copy(
                                homeUiMode = HomeUiMode.ROUTE_MODE,
                                routeResults = routeResults.toImmutableList(),
                                selectedRouteResult = routeResults.first()
                            )
                        }
                    } else {
                        Log.w(
                            "HomeViewModel",
                            "getRouteBetweenLocations: No routes returned from API"
                        )
                        // 빈 응답 시 fallback 사용
                        useFallbackRoutes()
                    }
                },
                onFailure = { error ->
                    Log.e("HomeViewModel", "getRouteBetweenLocations: Error fetching route", error)
                    useFallbackRoutes()
                }
            )
        }
    }

    private fun useFallbackRoutes() {
        Log.w("HomeViewModel", "No routes returned from API - showing empty routes")
    }

    private fun getBuildingInfoByResult(searchResult: SearchResult) {
        // TODO: 건물 정보 API 호출
        updateBuildingInfo(searchResult.id)
        setBottomSheetType(HomeBottomSheetType.BUILDING_INFO)
    }

    fun getBuildingInfoByMarker(selectedBuildingMarker: BuildingMarker) {
        // TODO: 건물 정보 API 호출
        updateBuildingInfo(selectedBuildingMarker.id)
        updateSelectedBuildingMarker(selectedBuildingMarker)
        setBottomSheetType(HomeBottomSheetType.BUILDING_INFO)
    }

    private fun updateSelectedBuildingMarker(selectedBuildingMarker: BuildingMarker) {
        _uiState.update {
            it.copy(
                selectedBuildingMarker = selectedBuildingMarker,
                selectedSpecialMarker = null,
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
        _uiState.update {
            it.copy(
                homeUiMode = homeUiMode,
                isBottomSheetExpanded = homeUiMode != HomeUiMode.FIND_MODE && homeUiMode != HomeUiMode.ROUTE_MODE,
            )
        }
    }

    fun setBottomSheetType(bottomSheetType: HomeBottomSheetType) {
        Log.d("HomeViewModel", "setBottomSheetType: $bottomSheetType")
        val isBottomSheetExpanded = bottomSheetType != HomeBottomSheetType.NONE
        _uiState.update {
            it.copy(
                bottomSheetType = bottomSheetType,
                isBottomSheetExpanded = isBottomSheetExpanded,
                showingDoorMarkers = if (bottomSheetType == HomeBottomSheetType.BUILDING_INFO) it.showingDoorMarkers else persistentListOf()
            )
        }
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
            }.toPersistentList()
            Log.d(
                "HomeViewModel",
                "updateToggleUiStates: updatedToggles=${
                    updatedToggles.filter { b -> b.isSelected }.map { a -> a.toggle }
                }"
            )
            val newShowingToggleMarkers = updatedToggles
                .filter { toggleUiState -> toggleUiState.isSelected }
                .map { toggleUiState ->
                    when (toggleUiState.toggle) {
                        MapToggle.CURB -> uiState.value.curbMarkers
                        MapToggle.SLOPE -> uiState.value.slopeMarkers
                        MapToggle.STAIRS -> uiState.value.stairsMarkers
                        MapToggle.SPECIAL_MARK -> uiState.value.specialMarkers
                    }
                }.toPersistentList()
            it.copy(
                homeUiMode = HomeUiMode.DEFAULT,
                toggleUiStates = updatedToggles,
                showingToggleMarkers = newShowingToggleMarkers,
                selectedSpecialMarker = null
            )
        }
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
                showingDoorMarkers = persistentListOf(),
            )
        }
    }
}