package com.ganaljigi.kubf.ui.helper.component.information

import android.graphics.drawable.Icon
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ganaljigi.kubf.ui.helper.component.shortcut.ShortCutTitle
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme

//정보 제목 박스
@Composable
fun InformationTitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(52.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "정보",
            style = KUBFAndroidTheme.typography.semiBold18.copy(
                fontSize = 18.sp
            )
        )
    }
}

//정보 하나 박스
@Composable
fun InfoItemBox(
    icon: ImageVector,
    label: String,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .width(304.dp)
            .wrapContentHeight()
            .padding(horizontal = 12.dp)
    ) {
        Row {
            //작은 박스1: 아이콘, 정보 종류 제목
            Row(
                modifier = Modifier
                    .size(width = 80.dp, height = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector=icon,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = label,
                    style = KUBFAndroidTheme.typography.semiBold14.copy(
                        fontSize = 14.sp
                    )
                )
            }
            Spacer(modifier = Modifier.width(12.dp))

            //작은 박스2: 안에 설명들?!
            Box(
                modifier = Modifier.fillMaxHeight()
            ) {

            }
        }
    }
}

//지도 박스
@Composable
fun MapBox(
    modifier: Modifier= Modifier
) {
    Box(
        modifier=modifier
            .size(width = 304.dp, height = 164.98.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(
                width = 1.dp,
                color = Color(0xFFF4F4F4),
                shape = RoundedCornerShape(10.dp)

            )
    ) {

    }
}

//전체 정보 박스

@Preview(showBackground = true)
@Composable
fun InformationTitlePreview() {
    InformationTitle()
}

@Preview(showBackground = true)
@Composable
fun MapBoxPreview() {
    MapBox()
}

@Preview(showBackground = true)
@Composable
fun InfoItemBoxPreview() {
    InfoItemBox(
        icon = Icons.Default.Phone,
        label = "전화번호"
    ) {
        Text(
            text = "02-450-3968",
            style = KUBFAndroidTheme.typography.regular14.copy(
                fontSize = 14.sp
            )
        )
    }
}