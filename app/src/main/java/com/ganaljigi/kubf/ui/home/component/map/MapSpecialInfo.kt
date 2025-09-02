package com.ganaljigi.kubf.ui.home.component.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ganalijigi.kubf.R
import com.ganaljigi.kubf.ui.theme.Gray2
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme

@Composable
fun MapSpecialInfo(
    modifier: Modifier = Modifier,
    painters: List<Painter>,
    description: String,
) {
    Column(
        modifier = modifier
            .background(Color.White, shape = RoundedCornerShape(20.dp))
            .border(
                width = 1.dp,
                color = Gray2.copy(alpha = 0.5f),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(12.dp)
            .widthIn(max = 214.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            painters.forEach { it ->
                Image(
                    painter = it,
                    contentDescription = description,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop,
                )
            }
        }
        Text(
            text = description,
            style = KUBFAndroidTheme.typography.semiBold14.copy(
                lineHeight = 22.sp,
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun MapSpecialInfoPreview() {
    MapSpecialInfo(
        painters = listOf(
            painterResource(id = R.drawable.ic_special_marker),
            painterResource(id = R.drawable.ic_special_marker)
        ),
        description = "특별한 장소",
    )
}