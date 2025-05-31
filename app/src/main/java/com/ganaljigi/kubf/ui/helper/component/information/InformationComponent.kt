package com.ganaljigi.kubf.ui.helper.component.information

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ganaljigi.kubf.ui.helper.component.shortcut.ShortCutTitle
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme

//정보 제목 박스
@Composable
fun InformationTitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(52.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "정보",
            style = KUBFAndroidTheme.typography.semiBold18.copy(
                fontSize = 18.sp
            )
        )
    }
}

//정보 하나 박스

//지도 박스

//전체 정보 박스

@Preview(showBackground = true)
@Composable
fun InformationTitlePreview() {
    InformationTitle()
}