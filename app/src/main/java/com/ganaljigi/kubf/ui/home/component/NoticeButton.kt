package com.ganaljigi.kubf.ui.home.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ganalijigi.kubf.R
import com.ganaljigi.kubf.ui.theme.MainGreen
import com.ganaljigi.kubf.ui.util.noRippleClickable

@Composable
fun NoticeButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    Surface(
        modifier = modifier
            .size(48.dp)
            .noRippleClickable(onClick),
        shape = CircleShape,
        color = MainGreen,
        shadowElevation = 2.dp
    ) {
        Icon(
            modifier = Modifier
                .padding(12.dp),
            painter = painterResource(R.drawable.ic_notice_white_24),
            contentDescription = "Notice",
            tint = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NoticeButtonPreview() {
    NoticeButton()
}