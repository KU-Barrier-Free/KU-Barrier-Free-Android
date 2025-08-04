package com.ganaljigi.kubf.ui.buildinginfo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ganaljigi.kubf.ui.common.component.KUBFSearchBar
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme

@Composable
fun SearchPopup() {
    var value by remember { mutableStateOf(TextFieldValue()) }
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .height(302.dp)
            .width(328.dp)
            .clip(shape = RoundedCornerShape(6))
            .background(Color.White)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "검색",
                    style = KUBFAndroidTheme.typography.medium15,
                )
            }
            KUBFSearchBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                value = value,
                onValueChange = {value = it},
                placeHolderText = "건물, 편의시설 검색",
                interactionSource = interactionSource,
                isFocused = interactionSource.collectIsFocusedAsState().value
                )
        }
    }
}

@Preview
@Composable
private fun SearchPreview() {
    SearchPopup()
}