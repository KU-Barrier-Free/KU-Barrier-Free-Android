package com.ganaljigi.kubf.ui.roominfo

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RoomPic(
    roomPicUrls: List<String>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { roomPicUrls.size })

    Box(modifier = modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
        ) { page ->
            AsyncImage(
                model = roomPicUrls[page],
                contentDescription = "강의실 사진",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        if (roomPicUrls.size > 1) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp)
                    .background(Color(0xFFFF212121)
                        .copy(alpha = 0.6f),
                        shape = RoundedCornerShape(20.dp)
                        )
            ) {
                Text(
                    text = "${pagerState.currentPage + 1}/${roomPicUrls.size}",
                    style = KUBFAndroidTheme.typography.semiBold13.copy(color = Color.White)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoomPicPreview() {
    RoomPic(
        roomPicUrls = listOf(
            "https://i.pinimg.com/1200x/10/dc/2e/10dc2ece8b542854d0e276a1114d6190.jpg",
            "https://i.pinimg.com/1200x/d9/5e/3f/d95e3f592893bd3df9e62df37c831638.jpg"
        )
    )
}