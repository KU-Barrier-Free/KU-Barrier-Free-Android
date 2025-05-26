package com.ganaljigi.kubf.ui.buildinginfo.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter

data class Room(
    val imageUrl:String,
    val number: String,
    val name: String,
    val use: String,
    val note: String

)

@Composable
fun RoomComponent(
    room: Room,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .clickable { onClick }
    ) {

        Column {
            Row {
                Text(
                    text = "${room.number}호 ${room.name}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            color = Color(0xFFD29027).copy(alpha = 0.1f)
                        )
                        .height(16.dp)
                        .wrapContentWidth(),
                ) {
                    Text(
                        text = "${room.use}",
                        color = Color(0xFFD29027),
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }
            Spacer(Modifier.height(14.dp))
            Row {
                Text(
                    text = "특이사항",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF999999)
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    text = "${room.note}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF999999),
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.height(12.dp))
            Row {
                Image(
                    painter = rememberAsyncImagePainter(room.imageUrl),
                    contentDescription = "${room.number} 이미지",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.height(84.dp)
                        .wrapContentWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.LightGray)
                )
                Spacer(Modifier.width(8.dp))
                Image(
                    painter = rememberAsyncImagePainter(room.imageUrl),
                    contentDescription = "${room.number} 이미지",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.height(84.dp)
                        .wrapContentWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.LightGray)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewRoom() {
    val room = Room("https://","101", "전산실습실", "강의실", "경사로")
    RoomComponent(room)
}