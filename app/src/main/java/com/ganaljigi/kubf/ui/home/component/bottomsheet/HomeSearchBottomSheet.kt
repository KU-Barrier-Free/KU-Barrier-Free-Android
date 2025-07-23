package com.ganaljigi.kubf.ui.home.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ganaljigi.kubf.ui.common.model.SearchResult
import com.ganaljigi.kubf.ui.theme.Gray2
import com.ganaljigi.kubf.ui.theme.MainGreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

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
        scrimColor = Color.Transparent,
        containerColor = Color.White,
        tonalElevation = 2.dp,
        dragHandle = {
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(28.dp)
                    .padding(top = 8.dp, bottom = 16.dp)
                    .background(
                        color = Gray2,
                        shape = RoundedCornerShape(4.dp)
                    )
            )
        }
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
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun HomeSearchBottomSheetEmptyListPreview() {

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