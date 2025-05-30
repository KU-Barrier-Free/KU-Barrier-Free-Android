package com.ganaljigi.kubf.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import com.ganaljigi.kubf.ui.common.model.MapToggle
import com.ganaljigi.kubf.ui.home.viewmodel.ToggleUiState
import com.ganaljigi.kubf.ui.theme.MainGreen
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.ganaljigi.kubf.ui.theme.LightGreen

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
    onToggleClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .background(
                if (isSelected) LightGreen else Color.White,
                shape = RoundedCornerShape(20.dp)
            )
            .then(
                if (isSelected)
                    Modifier
                        .border(
                            width = 1.dp,
                            color = MainGreen,
                            shape = RoundedCornerShape(20.dp)
                        )
                else Modifier
            )
            .clickable { onToggleClick() }
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            painter = painterResource(toggle.iconRes),
            contentDescription = toggle.label,
            tint = Color.Unspecified,
        )

        Text(
            text = toggle.label,
            style = KUBFAndroidTheme.typography.medium13
        )
    }
}

@Preview
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