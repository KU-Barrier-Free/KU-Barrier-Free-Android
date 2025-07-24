package com.ganaljigi.kubf.ui.home.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.ganalijigi.kubf.R
import com.ganaljigi.kubf.ui.common.model.SearchMode
import com.ganaljigi.kubf.ui.home.component.BarrierFreeInfoChip
import com.ganaljigi.kubf.ui.home.component.BarrierFreeInfoItem
import com.ganaljigi.kubf.ui.home.component.FindWayButton
import com.ganaljigi.kubf.ui.home.component.HomeToggle
import com.ganaljigi.kubf.ui.home.component.NoticeButton
import com.ganaljigi.kubf.ui.home.component.bottomsheet.HomeBuildingInfoSheetContent
import com.ganaljigi.kubf.ui.home.component.bottomsheet.HomeSearchBottomSheet
import com.ganaljigi.kubf.ui.home.component.find.HomeFindTopLocationComponent
import com.ganaljigi.kubf.ui.home.component.find.HomeRouteInfo
import com.ganaljigi.kubf.ui.home.component.map.MapComponent
import com.ganaljigi.kubf.ui.home.component.search.HomeInquiryDialog
import com.ganaljigi.kubf.ui.home.viewmodel.HomeBottomSheetType
import com.ganaljigi.kubf.ui.home.viewmodel.HomeUiMode
import com.ganaljigi.kubf.ui.home.viewmodel.HomeViewModel
import com.ganaljigi.kubf.ui.theme.Black
import com.ganaljigi.kubf.ui.theme.Gray2
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.ganaljigi.kubf.ui.util.noRippleClickable
import kotlinx.coroutines.launch
import java.lang.System.exit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    padding: PaddingValues,
    navigateToHelper: () -> Unit = { },
    navigateToSearch: (SearchMode) -> Unit = { },
    navigateToBuildingInfo: (Long) -> Unit = { },
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false,
        )
    )
    val bottomSheetState = scaffoldState.bottomSheetState
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(uiState.bottomSheetType) {
        scope.launch {
            when (uiState.bottomSheetType) {
                HomeBottomSheetType.SEARCH, HomeBottomSheetType.BUILDING_INFO -> {
                    if (bottomSheetState.isVisible.not()) {
                        bottomSheetState.expand()
                    }
                }

                else -> {
                    viewModel.setBottomSheetType(HomeBottomSheetType.NONE)
                    bottomSheetState.hide()
                }
            }
        }
    }

    BottomSheetScaffold(
        modifier = Modifier.padding(padding),
        scaffoldState = scaffoldState,
        sheetTonalElevation = 4.dp,
        sheetDragHandle = { },
        sheetContent = {
            when (uiState.bottomSheetType) {
                HomeBottomSheetType.SEARCH -> {
                    HomeSearchBottomSheet(
                        searchResults = uiState.searchResults,
                        onInquireClick = {
                            viewModel.setShowInquiryDialog(true)
                        },
                        onFromClick = { searchResult ->
                            viewModel.onFromClick(searchResult)
                        },
                        onToClick = { searchResult ->
                            viewModel.onToClick(searchResult)
                        },
                        onItemClick = { buildingId ->
                            navigateToBuildingInfo(buildingId)
                        },
                    )
                }

                HomeBottomSheetType.BUILDING_INFO -> {
                    HomeBuildingInfoSheetContent(
                        modifier = Modifier.fillMaxWidth(),
                        buildingInfo = uiState.buildingInfo,
                        onItemClick = { buildingId ->
                            navigateToBuildingInfo(buildingId)
                        },
                    )
                }

                else -> {}
            }
        }
    ) { innerPadding ->

        if (uiState.showInquiryDialog) {
            HomeInquiryDialog(
                inquiryField = uiState.inquiryField,
                onInquiryFieldChange = { viewModel.updateInquiryField(it) },
                onSubmit = {
                    viewModel.submitInquiry()
                },
                onDismissRequest = { viewModel.setShowInquiryDialog(false) }
            )
        }

        if (uiState.showSpecialImageDialog) {
            Dialog(
                onDismissRequest = { viewModel.setShowSpecialImageDialog(false) },
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(272.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    model = uiState.specialImageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }
        }

        MapComponent(
            modifier = Modifier
                .fillMaxSize(),
            cameraPosition = uiState.cameraPositionState,
            selectedBuildingMarker = uiState.selectedBuildingMarker,
            toggleMarkers = uiState.showingToggleMarkers,
            buildingMarkers = uiState.buildingMarkers
                .filter { it.id != uiState.selectedBuildingMarker?.id },
            doorMarkers = uiState.doorMarkers,
            onBuildingMarkerClick = { marker ->
                viewModel.getBuildingInfo(marker)
            },
            onSpecialMarkerClick = { viewModel.getSpecialMarkerInfo(it) },
            onSpecialInfoClick = { viewModel.setShowSpecialImageDialog(true, it) },
            setDefaultMode = { viewModel.setDefaultMode() },
            selectedSpecialMarker = uiState.selectedSpecialMarker,
            specialMarkerInfo = uiState.specialMarkerInfo,
        )
        AnimatedVisibility(
            visible = true
        ) { }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box {
                androidx.compose.animation.AnimatedVisibility(
                    visible = uiState.homeUiMode == HomeUiMode.FIND_MODE || uiState.homeUiMode == HomeUiMode.ROUTE_MODE,
                    enter = slideInVertically(
                        initialOffsetY = { -it / 2 }
                    ),
                    exit = slideOutVertically(
                        targetOffsetY = { -it }
                    ) + fadeOut()
                ) {
                    HomeFindTopLocationComponent(
                        modifier = Modifier
                            .padding(top = 12.dp),
                        fromLocationResult = uiState.fromLocation,
                        toLocationResult = uiState.toLocation,
                        onClose = { viewModel.setDefaultMode() },
                        onChange = { viewModel.changeFromToLocation() },
                        onFromLocationClick = {
                            navigateToSearch(SearchMode.FIND_FROM_LOCATION)
                        },
                        onToLocationClick = {
                            navigateToSearch(SearchMode.FIND_TO_LOCATION)
                        }
                    )
                }
//                if (uiState.homeUiMode == HomeUiMode.DEFAULT) {
                androidx.compose.animation.AnimatedVisibility(
                    visible = uiState.homeUiMode == HomeUiMode.DEFAULT
                            || uiState.homeUiMode == HomeUiMode.BARRIER_FREE_SHOWN,
                    enter = slideInVertically(
                        initialOffsetY = { -it / 2 }
                    ),
                    exit = slideOutVertically(
                        targetOffsetY = { -it }
                    ) + fadeOut()
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .height(IntrinsicSize.Min)
                                .padding(top = 12.dp, bottom = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Row(
                                modifier = Modifier
                                    .shadow(elevation = 3.dp, shape = RoundedCornerShape(10.dp))
                                    .noRippleClickable(
                                        onClick = {
                                            viewModel.setDefaultMode()
                                            navigateToSearch(SearchMode.SEARCH)
                                        }
                                    )
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .weight(1f)
                                    .padding(horizontal = 12.dp)
                                    .height(44.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_search_bar_leading),
                                    contentDescription = "검색 아이콘",
                                    tint = Color.Unspecified,
                                )
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = uiState.searchWord.text.ifEmpty { "건물, 편의시설 검색" },
                                    style = KUBFAndroidTheme.typography.medium15.copy(
                                        color = if (uiState.searchWord.text.isEmpty()) Gray2 else Black
                                    ),
                                )
                                if (uiState.searchWord.text.isNotEmpty()) {
                                    Icon(
                                        modifier = Modifier.noRippleClickable { viewModel.updateSearchWord() },
                                        painter = painterResource(R.drawable.ic_searchbar_close),
                                        contentDescription = "검색어 비우기",
                                        tint = Color.Unspecified,
                                    )
                                }
                            }
                            FindWayButton(
                                modifier = Modifier.size(44.dp)
                            ) { viewModel.setHomeUiMode(HomeUiMode.FIND_MODE) }
                        }
                        HomeToggle(
                            modifier = Modifier
                                .fillMaxWidth(),
                            toggleUiStates = uiState.toggleUiStates,
                            onToggleClick = { toggle ->
                                viewModel.updateToggleUiStates(toggle)
                                focusManager.clearFocus()
                            }
                        )
                    }
                }
            }

            Box {
                androidx.compose.animation.AnimatedVisibility(
                    visible = uiState.homeUiMode == HomeUiMode.BARRIER_FREE_SHOWN,
                    enter = slideInVertically(
                        initialOffsetY = { it / 2 }
                    ),
                    exit = slideOutVertically(
                        targetOffsetY = { it }
                    )
                ) {
                    BarrierFreeInfoItem(
                        onClick = {
                            viewModel.setHomeUiMode(HomeUiMode.DEFAULT)
                        }
                    )
                }
                androidx.compose.animation.AnimatedVisibility(
                    visible = uiState.homeUiMode == HomeUiMode.DEFAULT,
                    enter = slideInVertically(
                        initialOffsetY = { it / 2 }
                    ),
                    exit = slideOutVertically(
                        targetOffsetY = { it }
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        BarrierFreeInfoChip {
                            viewModel.setHomeUiMode(HomeUiMode.BARRIER_FREE_SHOWN)
                        }

                        NoticeButton {
                            navigateToHelper()
                        }
                    }
                }
                androidx.compose.animation.AnimatedVisibility(
                    visible = uiState.homeUiMode == HomeUiMode.ROUTE_MODE,
                    enter = slideInVertically(
                        initialOffsetY = { it / 2 }
                    ),
                    exit = slideOutVertically(
                        targetOffsetY = { it }
                    )
                ) {
                    HomeRouteInfo(
                        modifier = Modifier
                            .padding(16.dp),
                        selectedRoute = uiState.selectedRouteResult,
                        routeResults = uiState.routeResults,
                        onRouteSelected = { routeResult ->
                            viewModel.selectRoute(routeResult)
                        },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    KUBFAndroidTheme {
        HomeScreen(
            padding = PaddingValues(0.dp)
        )
    }
}