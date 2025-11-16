package com.ganaljigi.kubf.ui.helper.component.notice

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ganaljigi.kubf.ui.theme.Gray3
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme

//공지사항도 ID

//Helper화면의 NoticeBox의 Title
@Composable
fun NoticeTitle(
    onNavigateClick: () -> Unit,
) {
    Column(
        modifier = Modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(52.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "공지사항",
                style = KUBFAndroidTheme.typography.semiBold18.copy(
                    fontSize = 18.sp
                )
            )
        }
    }
}

//공지사항 박스
@Composable
fun NoticeItem(
    title: String,
    date: String,
    number: Int,
    index: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = if (index % 2 == 0) Color(0xFFF8FFFA) else Color.White

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Text(
            text = title,
            style = KUBFAndroidTheme.typography.medium14.copy(
                fontSize = 14.sp,
                lineHeight = 22.sp
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = date,
                style = KUBFAndroidTheme.typography.regular13.copy(
                    color = Gray3,
                    fontSize = 13.sp
                )
            )
            Text(
                text = "번호: $number",
                style = KUBFAndroidTheme.typography.regular13.copy(
                    color = Gray3,
                    fontSize = 13.sp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoticeBoxPreview() {
    NoticeTitle() {}
}