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
import com.ganaljigi.kubf.ui.home.model.SearchResult
import com.ganaljigi.kubf.ui.theme.Gray3
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.ganaljigi.kubf.ui.theme.MainGreen
import com.ganaljigi.kubf.ui.util.noRippleClickable

@Composable
fun HomeSearchBottomSheetSingleItem(
    modifier: Modifier = Modifier,
    searchResult: SearchResult,
    onFromClick: (SearchResult) -> Unit = {},
    onToClick: (SearchResult) -> Unit = {},
    onShowBuildingClick: (Long) -> Unit = {},
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(searchResult.icon),
                    contentDescription = null,
                    tint = Color.Unspecified,
                )
                Text(
                    text = searchResult.name,
                    style = KUBFAndroidTheme.typography.bold18,
                )
                Text(
                    text = searchResult.building,
                    style = KUBFAndroidTheme.typography.regular14.copy(
                        color = Gray3
                    ),
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = MainGreen.copy(alpha = 0.08f),
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

                Box(
                    modifier = Modifier
                        .background(
                            color = MainGreen,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .noRippleClickable {
                            onShowBuildingClick(searchResult.getBuildingId())
                        }
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 13.5.dp),
                        text = "건물 보기",
                        style = KUBFAndroidTheme.typography.regular14.copy(
                            color = Color.White
                        ),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeSearchBottomSheetWithSingleItemPreview() {
    HomeSearchBottomSheetSingleItem(
        searchResult = SearchResult(
            id = 1L,
            name = "카페 레스티오",
            building = "경영관",
            searchKeyword = "레스티"
        ),
        onFromClick = {},
        onToClick = {},
        onShowBuildingClick = {}
    )
}