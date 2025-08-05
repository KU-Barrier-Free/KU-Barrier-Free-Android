package com.ganaljigi.kubf.ui.roominfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ganaljigi.kubf.ui.theme.Gray3
import com.ganaljigi.kubf.ui.theme.Gray4
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Popup
import com.ganaljigi.kubf.ui.theme.MainGreen
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.window.Popup
import com.ganaljigi.kubf.ui.theme.MainGreen

//import com.ganaljigi.kubf.R

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
        // 상단 제목 + 강의실 칩
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$roomNumber ${roomName ?: ""}",
                style = KUBFAndroidTheme.typography.semiBold18.copy(fontWeight = FontWeight.Bold),
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(8.dp))
            if (lecture) {
                LectureChip()
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 수용 인원
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                //painter = painterResource(id = R.drawable.ic_roominfo_capacity),
                imageVector = Icons.Default.Person,
                contentDescription = "수용 인원",
                modifier = Modifier
                    .size(20.dp)
                    .padding(start = 16.dp, end = 8.dp),
                tint = Gray3
            )
            Text(
                text = "수용 인원",
                style = KUBFAndroidTheme.typography.regular14,
                color = Gray4
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Text(
                    text = "$capacity",
                    style = KUBFAndroidTheme.typography.semiBold16,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "명",
                    style = KUBFAndroidTheme.typography.regular14,
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 면적
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                //painter = painterResource(id = R.drawable.ic_roominfo_area),
                imageVector = Icons.Default.Info,
                contentDescription = "면적",
                modifier = Modifier
                    .size(20.dp)
                    .padding(start = 16.dp, end = 8.dp)
            )
            Text(
                text = "면적",
                style = KUBFAndroidTheme.typography.regular14,
                color = Gray4
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Text(
                    text = "$area m²",
                    style = KUBFAndroidTheme.typography.semiBold16,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "($floorSpace 평)",
                    style = KUBFAndroidTheme.typography.regular14,
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 호실 형태
        var showTooltip by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "호실 형태",
                    modifier = Modifier
                        .size(20.dp)
                        .padding(start = 16.dp, end = 8.dp),
                    tint = Gray3
                )
                Text(
                    text = "호실 형태",
                    style = KUBFAndroidTheme.typography.regular14,
                    color = Gray4
                )
                Spacer(modifier = Modifier.weight(1f))

                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "도움말",
                    modifier = Modifier
                        .size(18.dp)
                        .clickable { showTooltip = true },
                    tint = Gray3
                )
                Text(
                    text = roomType,
                    style = KUBFAndroidTheme.typography.semiBold16,
                    color = Color.Black
                )
            }

            if (showTooltip) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { showTooltip = false }
                )

                Popup (
                    alignment = Alignment.TopEnd,
                    offset = IntOffset(x = -16, y = 56)
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 248.dp, height = 82.dp)
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .padding(12.dp)
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(SpanStyle(color = MainGreen)) { append("평탄식") }
                                append("은 바닥이 전부 평평한 호실, ")
                                withStyle(SpanStyle(color = MainGreen)) { append("계단식") }
                                append("은 바닥에 단차가 있는 호실입니다.")
                            },
                            style = KUBFAndroidTheme.typography.regular14
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 관리 부서
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    //painter = painterResource(id = R.drawable.ic_roominfo_department),
                    imageVector = Icons.Default.Info,
                    contentDescription = "관리 부서",
                    modifier = Modifier
                        .size(20.dp)
                        .padding(start = 16.dp, end = 8.dp)
                )
                Text(
                    text = "관리 부서",
                    style = KUBFAndroidTheme.typography.regular14,
                    color = Gray4
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = department,
                    style = KUBFAndroidTheme.typography.semiBold16,
                    color = Color.Black,
                    modifier = Modifier.padding(end = 16.dp, bottom = 4.dp)
                )
            }
            Text(
                text = departmentNumber,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 16.dp),
                style = KUBFAndroidTheme.typography.regular14,
                color = Gray3
            )
        }
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