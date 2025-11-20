package com.ganaljigi.kubf.ui.helper.component.shortcut

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ganaljigi.kubf.ui.theme.MainGreen
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.ganaljigi.kubf.ui.theme.KUBFTypography
import com.ganalijigi.kubf.R
import com.ganaljigi.kubf.ui.theme.Gray1
import com.ganaljigi.kubf.ui.theme.Gray2

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

@Composable
fun ShortCutItem(
    modifier: Modifier = Modifier,
    text: String,
    iconResId: Int? = null,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(8.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .shadow(
                elevation = 6.dp,           // ← blur 느낌. 필요하면 4~8dp 사이에서 조절
                shape = shape,
                ambientColor = Color(0x1A000000),
                spotColor = Color(0x1A000000)
            )
            .clip(shape)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = shape,
            color = Color.White,
            border = BorderStroke(1.dp, Gray1),
            shadowElevation = 0.dp,
            tonalElevation = 0.dp,
            onClick = onClick
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (iconResId != null) {
                    Image(
                        painter = painterResource(id = iconResId),
                        contentDescription = "바로가기 아이콘",
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "기본 아이콘",
                        modifier = Modifier.size(20.dp),
                        tint = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = text,
                    color = MainGreen,
                    style = KUBFAndroidTheme.typography.medium14.copy(fontSize = 14.sp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    painter = painterResource(id = R.drawable.ic_helper_arrowleft_lightgray),
                    contentDescription = null,
                    tint = Gray2,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(24.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewShortCutTitle() {
    ShortCutTitle()
}