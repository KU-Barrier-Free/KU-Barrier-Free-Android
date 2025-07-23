package com.ganaljigi.kubf.ui.home.component.bottomsheet

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.ganaljigi.kubf.ui.home.model.SearchResult
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
                annotatedName = buildAnnotatedString {
                    append("카페 ")
                    withStyle(
                        style = SpanStyle(
                            color = MainGreen,
                        ),
                    ) {
                        append("레스티")
                    }
                    append("오")
                }
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
                annotatedName = buildAnnotatedString {
                    append("카페 ")
                    withStyle(
                        style = SpanStyle(
                            color = MainGreen,
                        ),
                    ) {
                        append("레스티")
                    }
                    append("오")
                }
            ),
            SearchResult(
                id = 2,
                name = "카페 레스티오",
                building = "공학관",
                annotatedName = buildAnnotatedString {
                    append("카페 ")
                    withStyle(
                        style = SpanStyle(
                            color = MainGreen,
                        ),
                    ) {
                        append("레스티")
                    }
                    append("오")
                }
            )
        )
    )
}