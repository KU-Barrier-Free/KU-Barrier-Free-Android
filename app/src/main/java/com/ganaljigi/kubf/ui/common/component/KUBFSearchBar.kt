package com.ganaljigi.kubf.ui.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ganalijigi.kubf.R
import com.ganaljigi.kubf.ui.theme.Gray2
import com.ganaljigi.kubf.ui.theme.Gray4
import com.ganaljigi.kubf.ui.theme.Green
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KUBFSearchBar(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    onValueCleared: () -> Unit = {},
    placeHolderText: String = "",
    interactionSource: MutableInteractionSource,
    isFocused: Boolean = false
) {
    Row(
        modifier = modifier
            .then(
                if (isFocused)
                    Modifier
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(10.dp),
                            color = Green
                        )
                        .background(Color.White)
                else Modifier
                    .shadow(1.dp, shape = RoundedCornerShape(10.dp), clip = true)
            )
            .background(Color.White)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_search_bar_leading),
            contentDescription = "검색 아이콘",
            tint = if (isFocused) Green else Color.Unspecified,
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 16.dp, horizontal = 8.dp),
            singleLine = true,
            interactionSource = interactionSource,
            cursorBrush = SolidColor(Gray4), // Cursor color
            textStyle = KUBFAndroidTheme.typography.medium15.copy(),
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = placeHolderText,
                        style = KUBFAndroidTheme.typography.medium15.copy(
                            color = Gray2
                        )
                    )
                }
                innerTextField()
            }
        )
        if (value.isNotEmpty()) {
            Icon(
                modifier = Modifier.clickable { onValueCleared() },
                painter = painterResource(R.drawable.ic_searchbar_close),
                contentDescription = "검색어 비우기",
                tint = Color.Unspecified,
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 100)
@Composable
private fun KUBFSearchBarPreview() {
    var value by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    Column {
        KUBFSearchBar(
            modifier = Modifier.padding(20.dp),
            value = value,
            onValueChange = { value = it },
            placeHolderText = "건물, 편의시설 검색",
            interactionSource = interactionSource,
            isFocused = interactionSource.collectIsFocusedAsState().value
        )
    }
}