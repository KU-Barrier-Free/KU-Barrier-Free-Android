package com.ganaljigi.kubf.ui.home.component.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ganalijigi.kubf.R
import com.ganaljigi.kubf.ui.common.model.SearchResult
import com.ganaljigi.kubf.ui.theme.Gray1
import com.ganaljigi.kubf.ui.theme.Gray3
import com.ganaljigi.kubf.ui.theme.Gray4
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.ganaljigi.kubf.ui.theme.MainGreen
import com.ganaljigi.kubf.ui.util.noRippleClickable

@Composable
fun HomeSearchContent(
    modifier: Modifier = Modifier,
    onKeywordClick: (String) -> Unit = {},
    onItemClick: (Long) -> Unit = {},
    popularKeywords: List<String> = emptyList(),
    searchResults: List<SearchResult> = emptyList(),
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                modifier = Modifier.padding(start = 12.dp, end = 4.dp),
                text = "인기 검색어",
                style = KUBFAndroidTheme.typography.regular12.copy(
                    color = Gray3,
                )
            )

            popularKeywords.take(4).forEach { toggle ->
                ToggleChip(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    searchKeyword = toggle,
                    onChipClick = onKeywordClick
                )
            }
        }

        if (searchResults.isEmpty()) {
            Spacer(modifier = Modifier.height(80.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "건물, 편의시설을 검색해보세요.",
                style = KUBFAndroidTheme.typography.regular14.copy(
                    color = Gray4
                )
            )
        } else {
            HorizontalDivider(
                color = Gray1
            )
            searchResults.forEach { result ->
                HomeSearchItem(
                    item = result,
                    onClick = { id -> onItemClick(id) },
                )

                HorizontalDivider(
                    color = Gray1
                )
            }
        }
    }
}

@Composable
private fun HomeSearchItem(
    modifier: Modifier = Modifier,
    item: SearchResult,
    onClick: (Long) -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .noRippleClickable { onClick(item.id) }
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_search_item_leading),
                contentDescription = "검색 아이콘",
            )
            Text(
                text = item.annotatedName,
                style = KUBFAndroidTheme.typography.medium16,
            )
        }
        Text(
            text = item.building,
            style = KUBFAndroidTheme.typography.regular14.copy(
                color = Gray3
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeSearchContentPreview() {
    HomeSearchContent(
        popularKeywords = listOf(
            "카페", "편의점", "복사실", "학술공간"
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