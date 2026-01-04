package com.ganaljigi.kubf.ui.home.viewmodel

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ganaljigi.kubf.data.remote.repository.BuildingRepository
import com.ganaljigi.kubf.data.remote.repository.HomeRepository
import com.ganaljigi.kubf.data.remote.repository.RouteRepository
import com.ganaljigi.kubf.mapper.toDoorMarkers
import com.ganaljigi.kubf.mapper.toHomeBuildingInfo
import com.ganaljigi.kubf.mapper.toRouteResults
import com.ganaljigi.kubf.mapper.toSpecialMarkerInfo
import com.ganaljigi.kubf.mapper.toUiState
import com.ganaljigi.kubf.ui.home.model.BuildingMarker
import com.ganaljigi.kubf.ui.home.model.MapToggle
import com.ganaljigi.kubf.ui.home.model.RouteResult
import com.ganaljigi.kubf.ui.home.model.SearchResult
import com.ganaljigi.kubf.ui.home.model.ToggleMarker
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    /**
     * 검색어를 업데이트하고, 관련 검색 결과를 가져옵니다.
     * @param newSearchWord 새로운 검색어
     */
    fun updateSearchWord(newSearchWord: TextFieldValue = TextFieldValue("")) {
        if (newSearchWord.text == uiState.value.searchWord.text) return
        _uiState.update { it.copy(searchWord = newSearchWord) }
        getSearchResults(newSearchWord.text)
    }

    /**
     * 문의하기 입력 필드를 업데이트합니다.
     * @param newInquiryField 새로운 문의 내용
     */
    fun updateInquiryField(newInquiryField: TextFieldValue) {
        _uiState.update { it.copy(inquiryField = newInquiryField) }
    }

    /**
     * 문의를 제출합니다.
     */
    fun submitInquiry() {
        // TODO: 문의 API 호출
        setShowInquiryDialog(false)
    }

    /**
     * 검색어를 기반으로 검색 결과를 가져옵니다.
     * @param newSearchWord 검색어
     */
    // 검색 결과 호출 API
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

    /**
     * 선택된 특이사항 마커의 정보를 가져옵니다.
     * @param selectedSpecialMarker 선택된 특이사항 마커
     */
    fun getSpecialMarkerInfo(selectedSpecialMarker: ToggleMarker) {
        viewModelScope.launch {
            homeRepository.getSpecialInfo(selectedSpecialMarker.id).fold(
                onSuccess = { response ->
                    _uiState.update {
                        it.copy(
                            specialMarkerInfo = response.toSpecialMarkerInfo(),
                            selectedBuildingMarker = null,
                            selectedSpecialMarker = selectedSpecialMarker,
                            bottomSheetType = HomeBottomSheetType.NONE,
                            searchResults = persistentListOf(),
                            showingBuildingMarkers = it.buildingMarkers
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

    /**
     * 검색 결과를 업데이트하고, 바텀 시트 표시 여부를 결정합니다.
     * @param newSearchResults 새로운 검색 결과 목록
     * @param showSheet 바텀 시트 표시 여부
     */
    fun updateSearchResults(
        newSearchResults: List<SearchResult> = uiState.value.searchResults,
        showSheet: Boolean = true,
    ) {
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

    /**
     * 단일 검색 결과를 처리합니다.
     * @param searchResult 단일 검색 결과
     */
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

    /**
     * '출발' 버튼 클릭을 처리합니다.
     * @param searchResult 선택된 검색 결과
     */
    fun onFromClick(searchResult: SearchResult) {
        setHomeUiMode(HomeUiMode.FIND_MODE)
        setBottomSheetType(HomeBottomSheetType.NONE)
        updateFromLocation(searchResult)
    }

    /**
     * '도착' 버튼 클릭을 처리합니다.
     * @param searchResult 선택된 검색 결과
     */
    fun onToClick(searchResult: SearchResult) {
        setHomeUiMode(HomeUiMode.FIND_MODE)
        setBottomSheetType(HomeBottomSheetType.NONE)
        updateToLocation(searchResult)
    }

    /**
     * 출발지를 업데이트하고, 필요한 경우 경로를 검색합니다.
     * @param fromLocation 출발지
     */
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

    /**
     * 도착지를 업데이트하고, 필요한 경우 경로를 검색합니다.
     * @param toLocation 도착지
     */
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

    /**
     * 출발지와 도착지를 바꿉니다.
     */
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

    /**
     * 출발지와 도착지 사이의 경로를 가져옵니다.
     */
    private fun getRouteBetweenLocations() {
        val fromLocation = _uiState.value.fromLocation
        val toLocation = _uiState.value.toLocation

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
                    }
                },
                onFailure = { error ->
                    Log.e("HomeViewModel", "getRouteBetweenLocations: Error fetching route", error)
                }
            )
        }
    }


    /**
     * 검색 결과로부터 건물 정보를 가져옵니다.
     * @param searchResult 검색 결과
     */
    private fun getBuildingInfoByResult(searchResult: SearchResult) {
        viewModelScope.launch {
            buildingRepository.getBuildingInfo(buildingId = searchResult.id)
                .onSuccess { response ->
                    _uiState.update {
                        it.copy(
                            buildingInfo = response.toHomeBuildingInfo(),
                            showingDoorMarkers = response.toDoorMarkers().toImmutableList(),
                            homeUiMode = HomeUiMode.DEFAULT
                        )
                    }
                    setBottomSheetType(HomeBottomSheetType.BUILDING_INFO)
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

    /**
     * 마커로부터 건물 정보를 가져옵니다.
     * @param selectedBuildingMarker 선택된 건물 마커
     */
    fun getBuildingInfoByMarker(selectedBuildingMarker: BuildingMarker) {
        viewModelScope.launch {
            buildingRepository.getBuildingInfo(buildingId = selectedBuildingMarker.id)
                .onSuccess { response ->
                    _uiState.update {
                        it.copy(
                            buildingInfo = response.toHomeBuildingInfo(),
                            showingDoorMarkers = response.toDoorMarkers().toImmutableList(),
                            homeUiMode = HomeUiMode.DEFAULT,
                        )
                    }
                    updateSelectedBuildingMarker(selectedBuildingMarker)
                    setBottomSheetType(HomeBottomSheetType.BUILDING_INFO)
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

    /**
     * 선택된 건물 마커를 업데이트합니다.
     * @param selectedBuildingMarker 선택된 건물 마커
     */
    private fun updateSelectedBuildingMarker(selectedBuildingMarker: BuildingMarker) {
        _uiState.update {
            it.copy(
                selectedBuildingMarker = selectedBuildingMarker,
                showingToggleMarkers = persistentListOf(),
                showingBuildingMarkers = persistentListOf(selectedBuildingMarker),
                selectedSpecialMarker = null,
                searchResults = persistentListOf(),
            )
        }
    }

    /**
     * 홈 화면 UI 모드를 설정합니다.
     * @param homeUiMode 홈 화면 UI 모드
     */
    fun setHomeUiMode(homeUiMode: HomeUiMode) {
        _uiState.update {
            it.copy(
                homeUiMode = homeUiMode,
                isBottomSheetExpanded = homeUiMode != HomeUiMode.FIND_MODE && homeUiMode != HomeUiMode.ROUTE_MODE,
            )
        }
    }

    fun setSelectedMarkersUnselected() {
        _uiState.update {
            it.copy(
                selectedBuildingMarker = null,
                selectedSpecialMarker = null,
            )
        }
    }

    /**
     * 바텀 시트 타입을 설정합니다.
     * @param bottomSheetType 바텀 시트 타입
     */
    fun setBottomSheetType(bottomSheetType: HomeBottomSheetType) {
        val isBottomSheetExpanded = bottomSheetType != HomeBottomSheetType.NONE
        _uiState.update {
            it.copy(
                bottomSheetType = bottomSheetType,
                isBottomSheetExpanded = isBottomSheetExpanded,
                showingDoorMarkers = if (bottomSheetType == HomeBottomSheetType.BUILDING_INFO) it.showingDoorMarkers else persistentListOf(),
                showingBuildingMarkers = if (bottomSheetType == HomeBottomSheetType.BUILDING_INFO) it.showingBuildingMarkers else it.buildingMarkers,
                showingToggleMarkers = if (bottomSheetType == HomeBottomSheetType.BUILDING_INFO) it.showingToggleMarkers else it.toggleUiStates.map { toggleUiState ->
                    when (toggleUiState.toggle) {
                        MapToggle.CURB -> uiState.value.curbMarkers
                        MapToggle.SLOPE -> uiState.value.slopeMarkers
                        MapToggle.STAIRS -> uiState.value.stairsMarkers
                        MapToggle.SPECIAL_MARK -> uiState.value.specialMarkers
                    }
                }.toPersistentList()
            )
        }
    }

    /**
     * 바텀 표시 여부를.
     * @param flag 바텀 시트 표시 여부
     */
    fun setBottomSheetVisible(flag: Boolean) {
        _uiState.update { it.copy(isBottomSheetExpanded = flag) }
    }

    /**
     * 문의하기 다이얼로그 표시 여부를 설정합니다.
     * @param showInquiryDialog 문의하기 다이얼로그 표시 여부
     */
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

    /**
     * 특이사항 이미지 다이얼로그 표시 여부를 설정합니다.
     * @param showSpecialImageDialog 특이사항 이미지 다이얼로그 표시 여부
     * @param imageUrl 이미지 URL 목록
     */
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

    /**
     * 지도 토글 UI 상태를 업데이트합니다.
     * @param toggle 지도 토글
     */
    fun updateToggleUiStates(toggle: MapToggle) {
        _uiState.update {
            val updatedToggles = it.toggleUiStates.map { toggleUiState ->
                if (toggleUiState.toggle == toggle) {
                    toggleUiState.copy(isSelected = !toggleUiState.isSelected)
                } else {
                    toggleUiState
                }
            }.toPersistentList()
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

    /**
     * 초기 데이터를 가져옵니다.
     */
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

    /**
     * 경로를 선택합니다.
     * @param routeResult 선택된 경로
     */
    fun selectRoute(routeResult: RouteResult) {
        _uiState.update {
            it.copy(
                selectedRouteResult = routeResult,
            )
        }
    }

    /**
     * 기본 모드로 설정합니다.
     */
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
                showingBuildingMarkers = it.buildingMarkers,
            )
        }
    }

    /**
     * 사용자의 현재 위치를 업데이트합니다.
     * @param location 사용자의 위치
     */
    fun updateUserLocation(location: LatLng) {
        _uiState.update {
            it.copy(userLocation = location)
        }
    }
}