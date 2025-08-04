package com.ganaljigi.kubf.ui.roominfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ganaljigi.kubf.ui.theme.Gray3
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme

@Composable
fun RoomInfoDefaultComponent(
    roomNumber: String,
    roomName: String?,
    lecture: Boolean,
    capacity: Int,
    area: Double,
    floorSpace: Double,
    roomType: String,
    department: String,
    departmentNumber: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "$roomNumber ${roomName ?: ""}",
                style = KUBFAndroidTheme.typography.semiBold16,
                color = Gray3
            )

            Spacer(modifier = Modifier.width(8.dp))

            if (lecture) {
                Text(
                    text = "강의실",
                    style = KUBFAndroidTheme.typography.medium14,
                    color = Gray3,
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 수용 인원
        RoomInfoRow(label = "수용 인원", value = "$capacity 명")

        // 면적
        RoomInfoRow(label = "면적", value = "${area}㎡ (${floorSpace}평)")

        // 특이사항

        // 호실 형태
        RoomInfoRow(label = "호실 형태", value = roomType)

        // 관리 부서
        RoomInfoRow(label = "관리 부서", value = department)

        // 연락처
        RoomInfoRow(label = "", value = departmentNumber)
    }
}

@Composable
fun LectureChip(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(20.dp)
            .width(42.dp)
            .background(
                color = Color(0x1AD29027),
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "강의실",
            color = Color(0xFFD29027),
            style = KUBFAndroidTheme.typography.regular13
        )
    }
}

@Composable
private fun RoomInfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = KUBFAndroidTheme.typography.medium14,
            color = Gray3
        )

        Text(
            text = value,
            style = KUBFAndroidTheme.typography.medium14,
            color = Gray3,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RoomInfoDefaultComponentPreview() {
    KUBFAndroidTheme {
        RoomInfoDefaultComponent(
            roomNumber = "102호",
            roomName = "전산실습실",
            lecture = true,
            capacity = 34,
            area = 60.6,
            floorSpace = 18.3,
            roomType = "평탄식",
            department = "정보인프라팀",
            departmentNumber = "010-0000-0000"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LectureChipPreview() {
    LectureChip()
}