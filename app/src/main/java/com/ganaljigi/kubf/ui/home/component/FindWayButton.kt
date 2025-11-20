package com.ganaljigi.kubf.ui.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ganalijigi.kubf.R
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme

@Composable
fun FindWayButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
) {
    Box(
        modifier = modifier
            .shadow(10.dp, RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .clickable(onClick = onClick)
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.Center),
            painter = painterResource(R.drawable.ic_find_way_button),
            contentDescription = null,
            tint = Color.Unspecified,
        )
        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 4.dp),
            text = "길찾기",
            style = KUBFAndroidTheme.typography.medium9,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FindWayButtonPreview() {
    FindWayButton(
        modifier = Modifier
            .size(44.dp)
            .padding(4.dp),
        onClick = {}
    )
}