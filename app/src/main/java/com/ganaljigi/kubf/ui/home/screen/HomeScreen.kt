package com.ganaljigi.kubf.ui.home.screen

import android.R.attr.value
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ganaljigi.kubf.ui.common.model.MapToggle
import com.ganaljigi.kubf.ui.common.model.SearchKeyword
import com.ganaljigi.kubf.ui.home.component.HomeSearchBar
import com.ganaljigi.kubf.ui.home.component.HomeToggle
import com.ganaljigi.kubf.ui.home.viewmodel.ToggleUiState
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme

@Composable
fun HomeScreen(
    padding: PaddingValues
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
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

        HomeSearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            onValueChange = { searchValue = it },
            onValueCleared = { searchValue = TextFieldValue("") },
            onChipClick = { searchKeyword ->
                searchValue = TextFieldValue(
                    text = searchKeyword.label,
                    selection = TextRange(searchKeyword.label.length)
                )
            },
            value = searchValue,
            searchKeywordEntry = SearchKeyword.entries
        )

        HomeToggle(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            toggleUiStates = toggleUiState,
            onToggleClick = { toggle ->
                toggleUiState = toggleUiState.toMutableList()
                    .map { if (it.toggle == toggle) it.copy(isSelected = !it.isSelected) else it }
                    .toList()
            }
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        padding = PaddingValues(0.dp)
    )
}