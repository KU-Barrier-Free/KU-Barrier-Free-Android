package com.ganaljigi.kubf.ui.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ganalijigi.kubf.R
import com.ganaljigi.kubf.ui.home.component.BarrierFreeInfoChip
import com.ganaljigi.kubf.ui.home.component.BarrierFreeInfoItem
import com.ganaljigi.kubf.ui.home.component.FindWayButton
import com.ganaljigi.kubf.ui.home.component.HomeToggle
import com.ganaljigi.kubf.ui.home.component.MapComponent
import com.ganaljigi.kubf.ui.home.component.NoticeButton
import com.ganaljigi.kubf.ui.home.component.bottomsheet.HomeSearchBottomSheet
import com.ganaljigi.kubf.ui.home.component.find.HomeFindLocationComponent
import com.ganaljigi.kubf.ui.home.viewmodel.HomeViewModel
import com.ganaljigi.kubf.ui.theme.Black
import com.ganaljigi.kubf.ui.theme.Gray2
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.ganaljigi.kubf.ui.util.noRippleClickable
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    padding: PaddingValues,
    navigateToHelper: () -> Unit = { },
    navigateToSearch: (String) -> Unit = { },
    navigateToBuildingInfo: (Int) -> Unit = { },
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val konkukUniversity = LatLng(37.5407, 127.0785)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(konkukUniversity, 16f)
    }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current


    if (uiState.showSearchBottomSheet) {
        HomeSearchBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { viewModel.setShowSearchBottomSheet(false) },
            searchResults = uiState.searchResults,
        )
    }

    MapComponent(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        cameraPosition = cameraPositionState
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (uiState.isFindMode) {
            HomeFindLocationComponent(
                modifier = Modifier
                    .padding(top = 12.dp),
                fromLocation = "",
                toLocation = "",
                onClose = { viewModel.setFindMode(false) },
                onChange = { viewModel.setFindMode(false) },
                onFromLocationClick = {
                    viewModel.setShowSearchBottomSheet(true)
                },
                onToLocationClick = {
                    viewModel.setShowSearchBottomSheet(true)
                }
            )
        } else {
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
                                    navigateToSearch("검색")
                                }
                            )
                            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
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
                        modifier = Modifier.fillMaxHeight()
                    ) { viewModel.setFindMode(true) }
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

        // 기본 모드일 경우에만 보임
        if (!uiState.isFindMode) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                BarrierFreeInfoItem(
                    visible = uiState.isBarrierFreeShown,
                ) {
                    viewModel.setBarrierFreeShown(uiState.isBarrierFreeShown.not())
                }
                if (!uiState.isBarrierFreeShown) {
                    BarrierFreeInfoChip {
                        viewModel.setBarrierFreeShown(true)
                    }

                    NoticeButton {
                        navigateToHelper()
                    }
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