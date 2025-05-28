package com.ganaljigi.kubf.ui.helper.component.notice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//Helper화면의 NoticeBox의 Title
@Composable
fun NoticeTitle(
    onNavigateClick: () -> Unit
) {
    Column(
        modifier = Modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(52.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "공지사항",
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )

            IconButton(onClick = onNavigateClick) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "공지사항 페이지로 이동"
                )
            }
        }
    }
}

//Notice화면의 공지사항 TopAppBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoticeTopAppBar(
    onBackClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            2
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .height(48.dp)
                    .statusBarsPadding(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "공지사항",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,

                    //fontFamily = FontFamily(Font(R.font.pretendard_semibold))
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "뒤로가기",
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        )
    )
}

@Preview(showBackground = true)
@Composable
fun NoticeBoxPreview() {
    NoticeTitle() {}
}

@Preview(showBackground = true)
@Composable
fun NoticeTopAppBarPreview() {
    NoticeTopAppBar(
        onBackClick = {}
    )
}