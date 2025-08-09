package com.ganaljigi.kubf.ui.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ganaljigi.kubf.ui.common.model.Convenience
import com.ganaljigi.kubf.ui.theme.Gray2
import com.ganaljigi.kubf.ui.theme.Gray4
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme

@Composable
fun ConvenienceChip(
    modifier: Modifier = Modifier,
    convenience: Convenience,
) {
    Row(
        modifier = modifier
            .background(
                color = Gray2.copy(alpha = 0.16f),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(convenience.iconRes),
            contentDescription = convenience.label,
            tint = Color.Unspecified,
        )
        Text(
            text = convenience.label,
            style = KUBFAndroidTheme.typography.regular13.copy(
                color = Gray4
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ConvenienceChipPreview() {
    ConvenienceChip(
        modifier = Modifier.padding(10.dp),
        convenience = Convenience.CONVENIENCE,
    )
}