package com.ganaljigi.kubf.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ganaljigi.kubf.ui.theme.Gray1
import com.ganaljigi.kubf.ui.theme.Gray4
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.ganaljigi.kubf.ui.theme.MainGreen
import com.ganaljigi.kubf.ui.util.noRippleClickable

@Composable
fun BarrierFreeInfoChip(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    Box {
        Box(
            modifier = modifier
                .matchParentSize()
                .blur(8.dp, BlurredEdgeTreatment.Unbounded)
        )
        Row(
            modifier = modifier
                .noRippleClickable(onClick = onClick)
                .background(
                    color = Color.White.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    color = Gray1,
                    width = 1.dp,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(vertical = 4.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .padding(2.dp)
                    .border(
                        color = Gray4,
                        width = 1.dp,
                        shape = CircleShape
                    )
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = "?",
                    style = KUBFAndroidTheme.typography.regular12.copy(
                        color = Gray4
                    ),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "배리어 프리",
                style = KUBFAndroidTheme.typography.regular12.copy(
                    color = MainGreen
                )
            )
            Text(
                text = "란?",
                style = KUBFAndroidTheme.typography.regular12.copy(
                    color = Gray4
                )
            )
        }
    }
}

@Preview
@Composable
private fun BarrierFreeInfoChipPreview() {
    BarrierFreeInfoChip()
}