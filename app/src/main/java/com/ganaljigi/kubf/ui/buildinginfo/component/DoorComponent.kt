package com.ganaljigi.kubf.ui.buildinginfo.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter

data class Door(
    val imageUrl: String,
    val label: String,
    val number: String,
    val wheel: Boolean
)

/**
 * 출입문 컴포넌트 - 문 사진과 휠체어 가능 여부
 * 가로로 스크롤 가능
 */
@Composable
fun DoorComponent(doors: List<Door>) {

    Column(
        modifier = Modifier
        //.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "출입문",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(end = 8.dp)
        ) {
            items(doors) { door ->
                DoorCard(door)
            }
        }
    }
}

/**
 * 출입문 정보 - 문 사진, 휠체어 여부
 * clickable인지? 클릭하면 상세 정보로 이동하는지 ?
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(80.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(door.imageUrl),
                    contentDescription = "${door.label} ${door.number}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.LightGray)
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFF656565))
                        .height(16.dp)
                        .wrapContentWidth(),
                    contentAlignment = Alignment.Center,

                    ) {
                    Text( // 출입문 이름
                        text = door.number,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 3.dp)
                    )
                }

            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "휠체어 진입",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (door.wheel) "가능 O" else "불가능 X",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                color = if (door.wheel) Color(0xFF3C8458) else Color(0xFF999999),

                )
        }
    }
}

@Preview
@Composable
private fun DoorPreview() {
    val doors = mutableListOf(Door("https://", "창의관", "A", false))
    doors.add(Door("https://", "창의관", "A-2", true))
    DoorComponent(doors)
}