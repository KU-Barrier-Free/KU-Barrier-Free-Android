package com.ganaljigi.kubf.ui.buildinginfo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ganaljigi.kubf.ui.buildinginfo.model.Door
import com.ganaljigi.kubf.ui.theme.Gray2
import com.ganaljigi.kubf.ui.theme.Gray3
import com.ganaljigi.kubf.ui.theme.Gray4
import com.ganaljigi.kubf.ui.theme.MainGreen
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme


/**
 * - 출입문 컴포넌트 - 문 사진과 휠체어 가능 여부,
 * - 가로로 스크롤 가능
 */
@Composable
fun DoorComponent(doors: List<Door>) {

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        items(doors) { door ->
            DoorCard(door)
        }
    }
}

/**
 * 출입문 정보
 * - 문 사진
 * - 휠체어 여부
 */
@Composable
fun DoorCard(door: Door) {
    Box(
        modifier = Modifier
            .width(80.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(80.dp)
            ) {
                AsyncImage(
                    model = door.imageUrl.first(),
                    contentDescription = "${door.label}",
                    modifier = Modifier.matchParentSize()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Gray2),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Gray4)
                        .height(16.dp)
                        .widthIn(min = 16.dp)
                        .wrapContentWidth(),
                    contentAlignment = Alignment.Center,
                    ) {
                    Text( // 출입문 이름
                        text = door.label,
                        style = KUBFAndroidTheme.typography.medium14,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 3.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "휠체어 진입",
                style = KUBFAndroidTheme.typography.regular13
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (door.wheel) "가능 O" else "불가능 X",
                style = KUBFAndroidTheme.typography.semiBold14.copy(
                    color = if (door.wheel) MainGreen else Gray3
                )
            )
        }
    }
}

@Preview
@Composable
private fun DoorPreview() {
//    val doors = mutableListOf(Door("https://", "창의관", "A", false))
//    doors.add(Door("https://", "창의관", "B", true))
//    DoorComponent(doors)
}