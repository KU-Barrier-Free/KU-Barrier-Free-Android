package com.ganaljigi.kubf.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ganaljigi.kubf.ui.common.component.KUBFSearchBar
import com.ganaljigi.kubf.ui.common.model.SearchKeyword
import com.ganaljigi.kubf.ui.theme.Gray1
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.ganaljigi.kubf.ui.theme.MainGreen
import com.ganaljigi.kubf.ui.util.noRippleClickable

@Composable
fun HomeSearchBar(
    modifier: Modifier = Modifier,
    onValueChange: (TextFieldValue) -> Unit = {},
    onValueCleared: () -> Unit = {},
    onChipClick: (SearchKeyword) -> Unit,
    onSearchKeyboardClick: () -> Unit = {},
    value: TextFieldValue,
    searchKeywordEntry: List<SearchKeyword>
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        KUBFSearchBar(
            modifier = Modifier
                .fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            onValueCleared = onValueCleared,
            onSearchKeyboardClick = onSearchKeyboardClick,
            placeHolderText = "건물, 편의시설 검색",
            interactionSource = interactionSource,
            isFocused = isFocused
        )
    }
}

@Composable
fun ToggleChip(
    modifier: Modifier = Modifier,
    searchKeyword: String,
    onChipClick: (String) -> Unit = {},
) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Gray1,
                shape = RoundedCornerShape(20.dp)
            )
            .noRippleClickable { onChipClick(searchKeyword) }
            .padding(horizontal = 8.dp, vertical = 7.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = searchKeyword,
            style = KUBFAndroidTheme.typography.regular12.copy(
                color = MainGreen
            ),
        )
    }

}

@Preview(showBackground = false, widthDp = 360, heightDp = 400)
@Composable
private fun HomeSearchBarPreview() {
    var value by remember {
        mutableStateOf(
            TextFieldValue(
                text = "",
                selection = TextRange.Zero,
            )
        )
    }
    HomeSearchBar(
        onValueChange = {},
        onValueCleared = {value = TextFieldValue("")},
        value = value,
        searchKeywordEntry = SearchKeyword.entries,
        onChipClick = {}
    )
}