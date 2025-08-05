package com.ganaljigi.kubf.ui.home.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ganaljigi.kubf.ui.theme.Gray4
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.ganaljigi.kubf.ui.theme.LightGreen
import com.ganaljigi.kubf.ui.theme.MainGreen
import com.ganaljigi.kubf.ui.util.noRippleClickable

@Composable
fun HomeSearchBottomSheetEmptyResult(
    modifier: Modifier = Modifier,
    searchKeyword: String = "",
    onInquireClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
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
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "검색 결과가 없습니다.\n찾고 싶으신 정보가 없다면 문의를 남겨주세요.",
            textAlign = TextAlign.Center,
            style = KUBFAndroidTheme.typography.regular14.copy(
                color = Gray4
            ),
        )
        Spacer(modifier = Modifier.height(41.dp))
        Box(
            modifier = Modifier
                .background(
                    color = LightGreen,
                    shape = RoundedCornerShape(20.dp)
                )
                .noRippleClickable { onInquireClick() }
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 13.5.dp),
                text = "문의하기",
                style = KUBFAndroidTheme.typography.regular14.copy(
                    color = MainGreen
                ),
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeSearchBottomSheetEmptyResultPreview() {
    HomeSearchBottomSheetEmptyResult(
        searchKeyword = "검색어",
        onInquireClick = {},
    )
}