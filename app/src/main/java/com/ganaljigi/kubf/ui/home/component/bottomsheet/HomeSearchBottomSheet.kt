package com.ganaljigi.kubf.ui.home.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ganaljigi.kubf.ui.home.model.SearchResult
import com.ganaljigi.kubf.ui.theme.Gray2
import com.ganaljigi.kubf.ui.theme.MainGreen

@Composable
fun HomeSearchBottomSheet(
    modifier: Modifier = Modifier,
    searchKeyword: String = "",
    searchResults: List<SearchResult> = emptyList(),
    onFromClick: (SearchResult) -> Unit = {},
    onToClick: (SearchResult) -> Unit = {},
    onItemClick: (Long) -> Unit = {},
    onInquireClick: () -> Unit = {},
) {
    Column {
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(100.dp)
                .height(28.dp)
                .padding(top = 8.dp, bottom = 16.dp)
                .background(
                    color = Gray2,
                    shape = RoundedCornerShape(4.dp)
                )
        )
        when (searchResults.size) {
            0 -> {
                HomeSearchBottomSheetEmptyResult(
                    modifier = modifier,
                    searchKeyword = searchKeyword,
                    onInquireClick = onInquireClick,
                )
            }

            1 -> {
                HomeSearchBottomSheetSingleItem(
                    modifier = modifier,
                    searchResult = searchResults.first(),
                    onFromClick = onFromClick,
                    onToClick = onToClick,
                    onShowBuildingClick = onItemClick,
                )
            }

            else -> {
                HomeSearchBottomSheetWithItemList(
                    modifier = modifier,
                    searchKeyword = searchKeyword,
                    searchResults = searchResults,
                    onFromClick = onFromClick,
                    onToClick = onToClick,
                    onItemClick = onItemClick,
                )
            }
        }
    }
}


@Preview
@Composable
private fun HomeSearchBottomSheetEmptyListPreview() {
    HomeSearchBottomSheet(
        searchKeyword = "레스티",
        searchResults = emptyList()
    )
}

@Preview
@Composable
private fun HomeSearchBottomSheetWithItemPreview() {
    HomeSearchBottomSheet(
        searchKeyword = "레스티",
        searchResults = listOf(
            SearchResult(
                id = 2,
                name = "카페 레스티오",
                building = "공학관",
                searchKeyword = "레스티"
            )
        )
    )
}

@Preview
@Composable
private fun HomeSearchBottomSheetWithItemListPreview() {
    HomeSearchBottomSheet(
        searchKeyword = "레스티",
        searchResults = listOf(
            SearchResult(
                id = 1,
                name = "카페 레스티오",
                building = "경영관",
                searchKeyword = "레스티"
            ),
            SearchResult(
                id = 2,
                name = "카페 레스티오",
                building = "공학관",
                searchKeyword = "레스티"
            )
        )
    )
}