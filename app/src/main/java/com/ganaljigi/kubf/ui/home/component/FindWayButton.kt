package com.ganaljigi.kubf.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter.State.Empty.painter
import com.ganalijigi.kubf.R
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.ganaljigi.kubf.ui.theme.MainGreen

@Composable
fun FindWayButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .background(
                brush = Brush.linearGradient(
                    colorStops = arrayOf(
                        0.0f to Color(0xFF3C8456),
                        0.62f to Color(0xFF34A656)
                    ),
                    start = Offset.Infinite.copy(y = 0f),
                    end = Offset.Infinite.copy(x = 0f)
                ), shape = RoundedCornerShape(8.dp)
            )
            .clickable(
                onClick = onClick,
//                indication = ripple(false, 80.dp),
//                interactionSource = null,
            )
            .padding(horizontal = 10.dp, vertical = 4.5.dp),
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_find_way),
                contentDescription = "Find Way",
                tint = Color.White,
            )
            Text(
                text = "길찾기",
                style = KUBFAndroidTheme.typography.medium9.copy(
                    color = Color.White
                )
            )
        }
    }
}

@Preview
@Composable
private fun FindWayButtonPreview() {
    FindWayButton(
        modifier = Modifier.size(44.dp),
        onClick = {}
    )
}