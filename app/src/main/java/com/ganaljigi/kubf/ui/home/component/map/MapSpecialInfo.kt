package com.ganaljigi.kubf.ui.home.component.map

import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ganaljigi.kubf.ui.theme.Gray2
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme

@Composable
fun MapSpecialInfo(
    description: String,
    modifier: Modifier = Modifier,
    painters: List<Painter>,
    scale: Float = 1f,
) {
    Log.d("ok", description)
    val cornerRadius by remember(scale) { mutableStateOf(20.dp * scale) }
    val imageSize by remember(scale) { mutableStateOf(80.dp * scale) }
    val fontSize by remember(scale) { mutableStateOf((14 * scale).coerceAtLeast(10f)) }
    val lineHeight by remember(scale) { mutableStateOf((22 * scale).coerceAtLeast(14f)) }

    Column(
        modifier = modifier
            .background(Color.White, shape = RoundedCornerShape(cornerRadius))
            .border(
                width = 1.dp,
                color = Gray2.copy(alpha = 0.5f),
                shape = RoundedCornerShape(cornerRadius)
            )
            .padding(12.dp * scale)
            .widthIn(max = 214.dp * scale),
        verticalArrangement = Arrangement.spacedBy(16.dp * scale),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp * scale)
        ) {
            painters.forEach {
                Image(
                    painter = it,
                    contentDescription = description,
                    modifier = Modifier
                        .size(imageSize)
                        .clip(RoundedCornerShape(10.dp * scale)),
                    contentScale = ContentScale.Crop,
                )
            }
        }
        Text(
            text = description,
            style = KUBFAndroidTheme.typography.semiBold14.copy(
                fontSize = fontSize.sp,
                lineHeight = lineHeight.sp,
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun MapSpecialInfoPreview() {
    MapSpecialInfo(
        painters = listOf(),
        description = "특별한 장소",
    )
}