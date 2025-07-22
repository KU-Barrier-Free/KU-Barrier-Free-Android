package com.ganaljigi.kubf.ui.home.component.bottomsheet

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ganaljigi.kubf.ui.common.model.SearchResult
import com.ganaljigi.kubf.ui.theme.MainGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSearchBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    onDismissRequest: () -> Unit = {},
    searchKeyword: String = "",
    searchResults: List<SearchResult> = emptyList(),
    onFromClick: (SearchResult) -> Unit = {},
    onToClick: (SearchResult) -> Unit = {},
    onItemClick: (Long) -> Unit = {},
    onInquireClick: () -> Unit = {},
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
    ) {
        when (searchResults.size) {
            0 -> {}
            1 -> {}
            else -> {}
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun HomeSearchBottomSheetEmpthListPreview() {
    HomeSearchBottomSheet(
        searchKeyword = "레스티",
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        ),
        searchResults = emptyList()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun HomeSearchBottomSheetWithItemPreview() {
    HomeSearchBottomSheet(
        searchKeyword = "레스티",
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        ),
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun HomeSearchBottomSheetWithItemListPreview() {
    HomeSearchBottomSheet(
        searchKeyword = "레스티",
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        ),
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