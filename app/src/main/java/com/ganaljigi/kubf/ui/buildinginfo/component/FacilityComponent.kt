package com.ganaljigi.kubf.ui.buildinginfo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ganaljigi.kubf.data.dto.buildingdata.Facility
import com.ganaljigi.kubf.ui.theme.Gray2
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FacilityComponent(
    facilities: List<Facility>,
) {
    FlowRow(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        facilities.forEach { facility ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Gray2.copy(alpha = 0.16f))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        painter = painterResource(facility.iconResId),
                        contentDescription = facility.label,
                        modifier = Modifier.size(20.dp),
                        tint = Color.Unspecified
                    )
                    Text(
                        text = facility.label,
                        style = KUBFAndroidTheme.typography.medium13
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun FacilityPreview() {
    val facilities = Facility.entries.toList()
    FacilityComponent(facilities)
}