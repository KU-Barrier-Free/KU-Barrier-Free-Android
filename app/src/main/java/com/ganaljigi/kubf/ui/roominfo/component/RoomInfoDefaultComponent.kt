package com.ganaljigi.kubf.ui.roominfo.component

import android.content.Intent
import android.net.Uri
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.ganalijigi.kubf.R
import com.ganaljigi.kubf.ui.theme.Gray3
import com.ganaljigi.kubf.ui.theme.Gray4
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.ganaljigi.kubf.ui.theme.MainGreen
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.TypedValue
import android.widget.PopupMenu
import androidx.core.content.res.ResourcesCompat
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Surface
import androidx.compose.ui.text.style.TextDecoration

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
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        // 상단 제목 + 강의실 칩
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$roomNumber ${roomName ?: ""}",
                style = KUBFAndroidTheme.typography.regular14.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
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
                painter = painterResource(R.drawable.ic_roominfo_capacity),
                contentDescription = "수용 인원",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(20.dp)
            )

            Spacer(Modifier.width(8.dp))

            Text(
                text = "수용 인원",
                style = KUBFAndroidTheme.typography.regular14,
                color = Gray4
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                verticalAlignment = Alignment.Bottom,
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
            Icon(
                painter = painterResource(R.drawable.ic_roominfo_area),
                contentDescription = "면적",
                tint = Gray4,
                modifier = Modifier
                    .size(20.dp)
            )

            Spacer(Modifier.width(8.dp))

            Text(
                text = "면적",
                style = KUBFAndroidTheme.typography.regular14,
                color = Gray4
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(
                    text = "$area ",
                    style = KUBFAndroidTheme.typography.semiBold16,
                    color = Color.Black
                )
                Text(
                    text = "m²",
                    style = KUBFAndroidTheme.typography.regular14,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "(",
                    style = KUBFAndroidTheme.typography.regular14,
                    color = Color.Black
                )
                Text(
                    text = "$floorSpace ",
                    style = KUBFAndroidTheme.typography.semiBold16,
                    color = Color.Black
                )
                Text(
                    text = "평)",
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
            var anchorTopPx by remember { mutableStateOf(0) }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_roominfo_roomtype), //TODO
                    contentDescription = "호실 형태 설명",
                    tint = Gray4,
                    modifier = Modifier
                        .size(20.dp)
                )

                Spacer(Modifier.width(8.dp))

                Text(
                    text = "호실 형태",
                    style = KUBFAndroidTheme.typography.regular14,
                    color = Gray4
                )
                Spacer(modifier = Modifier.weight(1f))

                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(R.drawable.ic_roominfo_roomtypequestion),
                    contentDescription = "도움말",
                    modifier = Modifier
                        .size(18.dp)
                        .clickable { showTooltip = true },
                    tint = Gray4
                )

                Spacer(Modifier.width(1.5.dp))

                Text(
                    text = roomType,
                    style = KUBFAndroidTheme.typography.semiBold16,
                    color = Color.Black
                )
            }

            if (showTooltip) {
                val density = LocalDensity.current
                val popupHeightPx = with(density) { 82.dp.roundToPx() }
                val gapPx = with(density) { 12.dp.roundToPx() }
                val xOffset = with(density) { 0.dp.roundToPx() }
                val yOffset = anchorTopPx - popupHeightPx - gapPx

                Popup (
                    alignment = Alignment.TopEnd,
                    offset = IntOffset(x = xOffset, y = yOffset),
                    properties = PopupProperties(focusable = true),
                    onDismissRequest = { showTooltip = false }
                ) {
//                    Box(
//                        modifier = Modifier
//                            .size(width = 248.dp, height = 82.dp)
//                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
//                            .clip(RoundedCornerShape(8.dp))
//                            .background(Color.White)
//                            .padding(12.dp),
//                        contentAlignment = Alignment.CenterStart
//                    ) {
//                        Text(
//                            text = buildAnnotatedString {
//                                withStyle(SpanStyle(color = MainGreen)) { append("평탄식") }
//                                append("은 바닥이 전부 평평한 호실, \n")
//                                withStyle(SpanStyle(color = MainGreen)) { append("계단식") }
//                                append("은 바닥에 단차가 있는 호실입니다.")
//                            },
//                            style = KUBFAndroidTheme.typography.regular14.copy(
//                                lineHeight = 25.sp,
//                                letterSpacing = (-0.025).em
//                            )
//                        )
//                    }
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color.White.copy(alpha = 0.9f),
                        border = BorderStroke(1.dp, Color.White),
                        shadowElevation = 4.dp
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = buildAnnotatedString {
                                withStyle(SpanStyle(color = MainGreen)) { append("평탄식") }
                                append("은 바닥이 전부 평평한 호실,\n")
                                withStyle(SpanStyle(color = MainGreen)) { append("계단식") }
                                append("은 바닥에 단차가 있는 호실입니다.")
                            },
                            style = KUBFAndroidTheme.typography.regular14.copy(
                                lineHeight = 25.sp,
                                letterSpacing = (-0.025).em
                            )
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
                Icon(painter = painterResource(id = R.drawable.ic_roominfo_department),
                    contentDescription = "관리 부서",
                    modifier = Modifier
                        .size(20.dp),
                    tint = Gray4
                )

                Spacer(Modifier.width(8.dp))

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
                    //modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            Spacer(Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DepartmentPhoneRow(departmentNumber = departmentNumber)
            }
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

@Composable
fun DepartmentPhoneRow(
    departmentNumber: String,
    modifier: Modifier = Modifier
) {
    val ctx = LocalContext.current
    var showMenu by remember { mutableStateOf(false) }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_roominfo_departmentnumber),
            contentDescription = "관리 부서 전화번호",
            modifier = Modifier.size(20.dp),
            tint = Gray3
        )

        //Spacer(Modifier.width(6.dp))

        Box {
            Text(
                text = departmentNumber,
                style = KUBFAndroidTheme.typography.regular13,
                color = Gray3,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(vertical = 2.dp)
                    .clickable { showMenu = true }
            )

            DropdownMenu (
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text("복사") },
                    onClick = {
                        copyToClipboard(ctx, departmentNumber)
                        Toast.makeText(ctx, "전화번호가 복사되었습니다.", Toast.LENGTH_SHORT).show()
                        showMenu = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("전화하기") },
                    onClick = {
                        copyToClipboard(ctx, departmentNumber)

                        val dial = normalizeForDial(departmentNumber)
                        if (dial.isBlank()) {
                            Toast.makeText(ctx, "유효한 전화번호가 없습니다.", Toast.LENGTH_SHORT).show()
                        } else {
                            val intent = Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.fromParts("tel", dial, null)
                            }
                            ctx.startActivity(intent)
                        }
                        showMenu = false
                    }
                )
            }
        }
    }
}

/** 숫자/앞자리 + 허용. 나머지 제거해서 다이얼러가 확실히 인식하도록. */ //지피띠니가 해줌ㅋ
private fun normalizeForDial(raw: String): String {
    val t = raw.trim()
    val out = StringBuilder()
    t.forEachIndexed { i, c ->
        if (c.isDigit() || (i == 0 && c == '+')) out.append(c)
    }
    return out.toString()
}

private fun copyToClipboard(ctx: Context, text: String) {
    val cm = ctx.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    cm.setPrimaryClip(ClipData.newPlainText("전화번호", text))
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

@Preview(showBackground = true)
@Composable
private fun _IconDebugPreview() {
    Box(
        Modifier
            .size(80.dp)
            .background(Color.Yellow) // 뒤 배경 확실히
            .padding(8.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_roominfo_capacity),
            contentDescription = null,
            tint = Color.Magenta,            // 눈에 띄는 색
            modifier = Modifier
                .size(48.dp)                 // 크게
                .align(Alignment.Center)
        )
    }
}