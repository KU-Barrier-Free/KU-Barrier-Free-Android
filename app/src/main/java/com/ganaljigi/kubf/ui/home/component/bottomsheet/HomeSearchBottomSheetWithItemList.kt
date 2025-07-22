package com.ganaljigi.kubf.ui.home.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ganaljigi.kubf.ui.common.model.SearchResult
import com.ganaljigi.kubf.ui.theme.Gray1
import com.ganaljigi.kubf.ui.theme.Gray3
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.ganaljigi.kubf.ui.theme.LightGreen
import com.ganaljigi.kubf.ui.theme.MainGreen
import com.ganaljigi.kubf.ui.util.noRippleClickable

@Composable
fun HomeSearchBottomSheetWithItemList(
    modifier: Modifier = Modifier,
    searchKeyword: String = "",
    searchResults: List<SearchResult> = emptyList(),
    onFromClick: (SearchResult) -> Unit = {},
    onToClick: (SearchResult) -> Unit = {},
    onItemClick: (Long) -> Unit = {},
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
        ) {
            Text(
                text = "\"$searchKeyword\"",
                style = KUBFAndroidTheme.typography.semiBold16.copy(
                    color = MainGreen
                ),
            )
            Text(
                text = " 검색 결과",
                style = KUBFAndroidTheme.typography.semiBold16,
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        searchResults.forEach { searchResult ->
            HomeSearchBottomSheetItem(
                modifier = Modifier.padding(vertical = 12.dp),
                searchResult = searchResult,
                onItemClick = onItemClick,
                onFromClick = onFromClick,
                onToClick = onToClick
            )
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = Gray1
            )
        }
    }
}

@Composable
private fun HomeSearchBottomSheetItem(
    modifier: Modifier = Modifier,
    searchResult: SearchResult,
    onItemClick: (Long) -> Unit = {},
    onFromClick: (SearchResult) -> Unit = {},
    onToClick: (SearchResult) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .noRippleClickable { onItemClick(searchResult.id) },
    ) {
        Row(
            modifier = Modifier.align(Alignment.Start),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Icon(
                painter = painterResource(searchResult.icon),
                contentDescription = null,
                tint = Color.Unspecified,
            )
            Text(
                text = searchResult.name,
                style = KUBFAndroidTheme.typography.medium16,
            )
            Text(
                text = searchResult.building,
                style = KUBFAndroidTheme.typography.regular14.copy(
                    color = Gray3
                ),
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.align(Alignment.End),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = LightGreen,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .noRippleClickable { onFromClick(searchResult) }
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 13.5.dp),
                    text = "출발",
                    style = KUBFAndroidTheme.typography.regular14.copy(
                        color = MainGreen
                    ),
                )
            }
            Box(
                modifier = Modifier
                    .background(
                        color = MainGreen,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .noRippleClickable { onToClick(searchResult) }
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 13.5.dp),
                    text = "도착",
                    style = KUBFAndroidTheme.typography.regular14.copy(
                        color = Color.White
                    ),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeSearchBottomSheetPreview() {
    HomeSearchBottomSheetWithItemList(
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