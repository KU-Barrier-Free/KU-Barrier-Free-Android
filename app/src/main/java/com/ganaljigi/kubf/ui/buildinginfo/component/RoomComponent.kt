package com.ganaljigi.kubf.ui.buildinginfo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ganalijigi.kubf.R
import com.ganaljigi.kubf.ui.buildinginfo.model.Room
import com.ganaljigi.kubf.ui.theme.Black
import com.ganaljigi.kubf.ui.theme.Gray3
import com.ganaljigi.kubf.ui.theme.Gray4
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme


/**
 * 강의실 썸네일 컴포넌트
 * - Room 데이터 받기
 * - 이미지 - 세로크기는 고정하고 비율은 유지
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RoomComponent(
    room: Room,
    onClick: () -> Unit = {}
) {
    val hasNote: Boolean = room.comment.isNotEmpty()
    val hasImage = room.roomImages.isNotEmpty()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                shadowElevation = 4.dp.toPx()
                ambientShadowColor = Color.Black.copy(alpha = 0.2f)
                spotShadowColor = Color.Black.copy(alpha = 0.3f)
                shape = RoundedCornerShape(16.dp)
                clip = false

            }
            .clip(RoundedCornerShape(20.dp))
            .background(
                color = Color(0xFFFFFFFF)
            )
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier.matchParentSize(),
            contentAlignment = Alignment.TopEnd
        ) {
            Icon(painter = painterResource(R.drawable.ic_chevron_right),
                contentDescription = "이동")
        }
        Column {
            FlowRow(modifier = Modifier.width(300.dp)) {
                Text(
                    text = "${room.number} ${room.name}",
                    style = KUBFAndroidTheme.typography.semiBold16,
                    fontWeight = FontWeight.Bold,
                    color = Black
                )
                Spacer(Modifier.width(8.dp))
                if (room.isLecture){
                    Box(
                        modifier = Modifier
                            .height(20.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                color = Color(0xFFD29027).copy(alpha = 0.1f)
                            )
                            .wrapContentWidth(),
                        //contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "강의실",
                            color = Color(0xFFD29027),
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(horizontal = 4.dp).padding(top =1.dp)
                        )
                    }
                }
            }

            if (hasNote) {
                Spacer(Modifier.height(14.dp))
                Row {
                    Text(
                        text = "특이사항",
                        style = KUBFAndroidTheme.typography.regular13,
                        color = Gray3
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        text = room.comment,
                        style = KUBFAndroidTheme.typography.regular14,
                        color = Gray3,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            if (hasImage) {
                Spacer(Modifier.height(12.dp))
                Row {
                    room.roomImages.forEach { url ->
                        AsyncImage(
                            model = url,
                            contentDescription = room.number + "이미지",
                            modifier = Modifier
                                .height(84.dp)
                                .wrapContentWidth()
                                .clip(RoundedCornerShape(10.dp))
                        )
                        Spacer(Modifier.width(8.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewRoom() {
    Box(
        modifier = Modifier.background(Color.White).size(500.dp),
        contentAlignment = Alignment.Center
    ){
        var urlL = mutableListOf("http")
        val room = Room(10,urlL,"101","전산실습실",true,"")
        RoomComponent(room)
    }

}