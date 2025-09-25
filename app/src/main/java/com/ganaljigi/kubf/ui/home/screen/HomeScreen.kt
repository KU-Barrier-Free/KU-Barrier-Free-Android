package com.ganaljigi.kubf.ui.home.screen

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ganalijigi.kubf.R
import com.ganaljigi.kubf.ui.common.component.PermissionDialog
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
import com.ganaljigi.kubf.ui.home.component.map.HomeSpecialMarkDialog
import com.ganaljigi.kubf.ui.home.component.map.MapComponent
import com.ganaljigi.kubf.ui.home.component.search.HomeInquiryDialog
import com.ganaljigi.kubf.ui.home.viewmodel.HomeBottomSheetType
import com.ganaljigi.kubf.ui.home.viewmodel.HomeUiMode
import com.ganaljigi.kubf.ui.home.viewmodel.HomeViewModel
import com.ganaljigi.kubf.ui.theme.Black
import com.ganaljigi.kubf.ui.theme.Gray2
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.ganaljigi.kubf.ui.util.noRippleClickable
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch

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
    val activity = LocalContext.current as Activity
    val context = LocalContext.current
    var isLocationPermissionGranted by remember { mutableStateOf(false) }
    var shouldShowRationale by remember { mutableStateOf(false) }
    var openAppSettingsDialog by remember { mutableStateOf(false) }
    val locationPermissionResultLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
    ) { permissions ->
        val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

        if (fineLocationGranted || coarseLocationGranted) {
            Log.d("TAG", "Location permission granted")
            isLocationPermissionGranted = true
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION) ||
                ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                shouldShowRationale = true
            } else {
                openAppSettingsDialog = true
            }
        }
    }
    LaunchedEffect(Unit) {
        val fineLocationGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val coarseLocationGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        if (fineLocationGranted || coarseLocationGranted) {
            isLocationPermissionGranted = true
        } else {
            locationPermissionResultLauncher.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ))
        }
    }

    BackHandler(enabled = uiState.homeUiMode != HomeUiMode.DEFAULT) {
        viewModel.setDefaultMode()
    }

    LaunchedEffect(uiState.isBottomSheetExpanded, uiState.bottomSheetType) {
        Log.d(
            "HomeScreen",
            "Bottom sheet state changed, ${uiState.bottomSheetType} ${uiState.isBottomSheetExpanded}"
        )
        if (uiState.isBottomSheetExpanded && uiState.bottomSheetType != HomeBottomSheetType.NONE) {
            if (bottomSheetState.isVisible.not()) {
                scope.launch {
                    bottomSheetState.expand()
                }
            }
        } else {
            scope.launch {
                bottomSheetState.hide()
            }
        }
    }

    BottomSheetScaffold(
        containerColor = Color.White,
        sheetContainerColor = Color.White,
        modifier = Modifier.padding(padding),
        scaffoldState = scaffoldState,
        sheetTonalElevation = 4.dp,
        sheetDragHandle = { },
        sheetContent = {
            if (uiState.isBottomSheetExpanded) {
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
        }
    ) { innerPadding ->

        if (uiState.isBottomSheetExpanded) {
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
            HomeSpecialMarkDialog(
                onDismissRequest = { viewModel.setShowSpecialImageDialog(false) },
                imageUrls = uiState.specialImageUrl,
            )
        }

        MapComponent(
            modifier = Modifier
                .fillMaxSize(),
            isLocationPermissionGranted = isLocationPermissionGranted,
            cameraPosition = uiState.cameraPositionState,
            selectedBuildingMarker = uiState.selectedBuildingMarker,
            selectedToggles = uiState.toggleUiStates.filter { it.isSelected }.toPersistentList(),
            buildingMarkers = uiState.buildingMarkers
                .filter { it.id != uiState.selectedBuildingMarker?.id },
            doorMarkers = uiState.showingDoorMarkers.takeIf { uiState.homeUiMode == HomeUiMode.DEFAULT }
                ?: persistentListOf(),
            curbMarkers = uiState.curbMarkers.takeIf { uiState.homeUiMode == HomeUiMode.DEFAULT }
                ?: persistentListOf(),
            slopeMarkers = uiState.slopeMarkers.takeIf { uiState.homeUiMode == HomeUiMode.DEFAULT }
                ?: persistentListOf(),
            stairsMarkers = uiState.stairsMarkers.takeIf { uiState.homeUiMode == HomeUiMode.DEFAULT }
                ?: persistentListOf(),
            specialMarkers = uiState.specialMarkers.takeIf { uiState.homeUiMode == HomeUiMode.DEFAULT }
                ?: persistentListOf(),
//            routeResults = uiState.routeResults,
            selectedRouteResult = uiState.selectedRouteResult,
            onBuildingMarkerClick = { marker ->
                viewModel.getBuildingInfoByMarker(selectedBuildingMarker = marker)
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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            Box(
                modifier = Modifier.align(Alignment.TopCenter)
            ) {
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

            Box(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
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
            }
            Box(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
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
            }
            Box(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
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
                            .padding(vertical = 16.dp),
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

    PermissionDialog(
        context = context,
        showRationaleDialog = shouldShowRationale,
        showOpenSettingsDialog = openAppSettingsDialog,
        onDismissRationaleDialog = { shouldShowRationale = false },
        onDismissOpenAppSettingsDialog = { openAppSettingsDialog = false },
        onRetryClick = {
            shouldShowRationale = false
            locationPermissionResultLauncher.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ))
        },
    )
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