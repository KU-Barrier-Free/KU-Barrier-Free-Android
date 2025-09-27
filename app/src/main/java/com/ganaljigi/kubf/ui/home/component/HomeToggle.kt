package com.ganaljigi.kubf.ui.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ganaljigi.kubf.ui.home.model.MapToggle
import com.ganaljigi.kubf.ui.home.viewmodel.ToggleUiState
import com.ganaljigi.kubf.ui.theme.MainGreen
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.ganaljigi.kubf.ui.theme.LightGreen
import com.ganaljigi.kubf.ui.util.noRippleClickable

@Composable
fun HomeToggle(
    modifier: Modifier = Modifier,
    toggleUiStates: List<ToggleUiState>,
    onToggleClick: (MapToggle) -> Unit = {},
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        toggleUiStates.forEach {
            HomeToggleChip(
                modifier = Modifier,
                isSelected = it.isSelected,
                toggle = it.toggle,
                onToggleClick = { onToggleClick(it.toggle) }
            )
        }
    }
}

@Composable
fun HomeToggleChip(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    toggle: MapToggle,
    onToggleClick: () -> Unit = {},
) {
    Surface(
        modifier = modifier
            .noRippleClickable { onToggleClick() },
        shadowElevation = 2.dp,
        shape = RoundedCornerShape(20.dp),
        color =
            if (isSelected) LightGreen else Color.White,
        border = if (isSelected) {
            BorderStroke(
                width = 1.dp,
                color = MainGreen
            )
        } else null
    ) {
        Row(
            modifier = Modifier
                .heightIn(min = 32.dp)
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(toggle.toggleIconRes),
                contentDescription = toggle.label,
                tint = Color.Unspecified,
            )

            Text(
                text = toggle.label,
                style = KUBFAndroidTheme.typography.medium13.copy(
                    color = if (isSelected) MainGreen else Color.Black
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeTogglePreview() {
    HomeToggle(
        toggleUiStates = MapToggle.entries.map {
            ToggleUiState(
                toggle = it,
                isSelected = it == MapToggle.CURB
            )
        }
    )
}