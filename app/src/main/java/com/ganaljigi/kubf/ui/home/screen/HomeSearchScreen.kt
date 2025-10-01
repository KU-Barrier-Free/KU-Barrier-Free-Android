package com.ganaljigi.kubf.ui.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ganaljigi.kubf.ui.common.model.SearchMode
import com.ganaljigi.kubf.ui.home.component.search.HomeSearchBar
import com.ganaljigi.kubf.ui.home.component.search.HomeSearchContent
import com.ganaljigi.kubf.ui.home.component.search.HomeSearchTopBar
import com.ganaljigi.kubf.ui.home.viewmodel.HomeViewModel
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme

@Composable
fun HomeSearchScreen(
    padding: PaddingValues,
    searchMode: SearchMode,
    navigateUp: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        viewModel.getSearchResults()
    }
    LaunchedEffect(Unit) {
        viewModel.updateSearchWord(TextFieldValue(""))
        viewModel.updateSearchResults(emptyList(), false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(padding)
    ) {
        HomeSearchTopBar(
            title = searchMode.title,
            onClick = { navigateUp() }
        )
        Spacer(modifier = Modifier.height(8.dp))
        HomeSearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .focusRequester(focusRequester),
            onValueChange = viewModel::updateSearchWord,
            onValueCleared = viewModel::updateSearchWord,
            onSearchKeyboardEntered = {
                if (searchMode == SearchMode.SEARCH) {
                    viewModel.updateSearchWord(uiState.searchWord)
                    viewModel.updateSearchResults()
                    navigateUp()
                }
            },
            value = uiState.searchWord,
        )
        HomeSearchContent(
            onKeywordClick = { searchKeyword ->
                viewModel.updateSearchWord(
                    TextFieldValue(
                        text = searchKeyword,
                        selection = TextRange(searchKeyword.length)
                    )
                )
            },
            onItemClick = {
                when (searchMode) {
                    SearchMode.SEARCH -> viewModel.updateSearchResults(listOf(it))
                    SearchMode.FIND_FROM_LOCATION -> viewModel.onFromClick(it)
                    SearchMode.FIND_TO_LOCATION -> viewModel.onToClick(it)
                }
                navigateUp()
            },
            searchResults = uiState.searchResults,
            popularKeywords = uiState.popularKeywords,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeSearchScreenPreview() {
    KUBFAndroidTheme {
        HomeSearchScreen(
            padding = PaddingValues(),
            searchMode = SearchMode.SEARCH,
        )
    }
}