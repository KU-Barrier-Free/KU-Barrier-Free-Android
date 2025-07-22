package com.ganaljigi.kubf.ui.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ganaljigi.kubf.ui.common.model.MapToggle
import com.ganaljigi.kubf.ui.common.model.SearchKeyword
import com.ganaljigi.kubf.ui.home.component.BarrierFreeInfoChip
import com.ganaljigi.kubf.ui.home.component.BarrierFreeInfoItem
import com.ganaljigi.kubf.ui.home.component.FindWayButton
import com.ganaljigi.kubf.ui.home.component.HomeSearchBar
import com.ganaljigi.kubf.ui.home.component.HomeToggle
import com.ganaljigi.kubf.ui.home.component.MapComponent
import com.ganaljigi.kubf.ui.home.component.NoticeButton
import com.ganaljigi.kubf.ui.home.viewmodel.ToggleUiState
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun HomeScreen(
    padding: PaddingValues,
    navigateToHelper: () -> Unit = { },
    navigateToBuildingInfo: (Int) -> Unit = { },
) {
    var searchValue by remember {
        mutableStateOf(
            TextFieldValue(text = "")
        )
    }
    var toggleUiState by remember {
        mutableStateOf(
            MapToggle.entries.map {
                ToggleUiState(
                    isSelected = it == MapToggle.SPECIAL_MARK,
                    toggle = it
                )
            }
        )
    }
    var isBarrierFreeShown by remember {
        mutableStateOf(false)
    }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val konkukUniversity = LatLng(37.5407, 127.0785)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(konkukUniversity, 16f)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 20.dp, vertical = 15.dp)
            ) {
                Text(
                    text = "KU-Barrier Free",
                    style = KUBFAndroidTheme.typography.medium20
                )
            }
        }
    ) { innerPadding ->

        MapComponent(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            cameraPosition = cameraPositionState
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    HomeSearchBar(
                        modifier = Modifier
                            .weight(1f)
                            .focusRequester(focusRequester),
                        onValueChange = { searchValue = it },
                        onValueCleared = { searchValue = TextFieldValue("") },
                        onChipClick = { searchKeyword ->
                            searchValue = TextFieldValue(
                                text = searchKeyword.label,
                                selection = TextRange(searchKeyword.label.length)
                            )
                            focusManager.clearFocus()
                        },
                        onSearchKeyboardClick = {
                            // TODO:  검색 기능
                            focusManager.clearFocus()
                        },
                        value = searchValue,
                        searchKeywordEntry = SearchKeyword.entries
                    )
                    FindWayButton(
                        modifier = Modifier.fillMaxHeight()
                    ) { // TODO: 길찾기  }
                    }
                }

                HomeToggle(
                    modifier = Modifier
                        .fillMaxWidth(),
                    toggleUiStates = toggleUiState,
                    onToggleClick = { toggle ->
                        toggleUiState = toggleUiState.toMutableList()
                            .map { if (it.toggle == toggle) it.copy(isSelected = !it.isSelected) else it }
                            .toList()
                    }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                BarrierFreeInfoItem(
                    visible = isBarrierFreeShown,
                ) {
                    isBarrierFreeShown = !isBarrierFreeShown
                }
                if (!isBarrierFreeShown) {
                    BarrierFreeInfoChip {
                        isBarrierFreeShown = !isBarrierFreeShown
                    }

                    NoticeButton {
                        navigateToHelper()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        padding = PaddingValues(0.dp)
    )
}