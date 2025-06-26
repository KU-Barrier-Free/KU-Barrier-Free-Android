package com.ganaljigi.kubf.ui.helper.component.shortcut

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.ganaljigi.kubf.ui.theme.KUBFTypography

//바로가기 제목 박스
@Composable
fun ShortCutTitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(52.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "바로가기",
            style = KUBFAndroidTheme.typography.semiBold18.copy(
                fontSize = 18.sp
            )
        )
    }
}

//바로가기 작은 박스
@Composable
fun ShortCutItem(
    modifier: Modifier = Modifier,
    text: String,
    iconResId: Int? = null,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(48.dp),
        shape = RoundedCornerShape(8.dp),
        color = Color.White,
        shadowElevation = 2.dp,
        border = BorderStroke(1.dp, Color(0xFFEEEEEE)),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 아이콘이 null이면 기본 아이콘 또는 빈 박스 대체
            if (iconResId != null) {
                Image(
                    painter = painterResource(id = iconResId),
                    contentDescription = "바로가기 아이콘",
                    modifier = Modifier.size(20.dp)
                )
            } else {
                // 기본 아이콘으로 대체 (예시: search 아이콘)
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight, // 또는 Search 등
                    contentDescription = "기본 아이콘",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Gray
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = text,
                color = Color(0xFF3C8458),
                style = KUBFAndroidTheme.typography.medium14.copy(
                    fontSize = 14.sp
                )
            )
            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "해당 바로가기 웹뷰 ㄱㄱ",
                tint = Color.LightGray,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(20.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewShortCutTitle() {
    ShortCutTitle()
}