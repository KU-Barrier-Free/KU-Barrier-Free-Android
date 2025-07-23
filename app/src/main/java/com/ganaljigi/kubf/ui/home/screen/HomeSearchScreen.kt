package com.ganaljigi.kubf.ui.home.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.ganaljigi.kubf.ui.common.model.SearchKeyword
import com.ganaljigi.kubf.ui.home.component.search.HomeSearchBar
import com.ganaljigi.kubf.ui.home.component.search.HomeSearchContent
import com.ganaljigi.kubf.ui.home.component.search.HomeSearchTopBar
import com.ganaljigi.kubf.ui.home.viewmodel.HomeViewModel
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme

@Composable
fun HomeSearchScreen(
    padding: PaddingValues,
    title: String,
    navigateUp: () -> Unit = {},
    viewModel: @Composable (NavBackStackEntry) -> HomeViewModel = { hiltViewModel() },
) {
    var searchValue by remember {
        mutableStateOf(
            TextFieldValue(text = "")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        HomeSearchTopBar(
            title = title,
            onClick = { navigateUp() }
        )
        Spacer(modifier = Modifier.height(8.dp))
        HomeSearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onValueChange = { searchValue = it },
            onValueCleared = { searchValue = TextFieldValue("") },
            onChipClick = { searchKeyword ->
                searchValue = TextFieldValue(
                    text = searchKeyword.label,
                    selection = TextRange(searchKeyword.label.length)
                )
            },
            onSearchKeyboardClick = { navigateUp() },
            value = searchValue,
            searchKeywordEntry = SearchKeyword.entries
        )
        HomeSearchContent(
            onKeywordClick = { searchKeyword ->
                searchValue = TextFieldValue(
                    text = searchKeyword,
                    selection = TextRange(searchKeyword.length)
                )
            },
            onItemClick = { TODO() },
            popularKeywords = listOf(
                "카페", "편의점", "복사실", "학술공간"
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeSearchScreenPreview() {
    KUBFAndroidTheme {
        HomeSearchScreen(
            padding = PaddingValues(),
            title = "검색"
        )
    }
}