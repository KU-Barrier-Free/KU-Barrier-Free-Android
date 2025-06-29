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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ganaljigi.kubf.ui.theme.Gray2
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme

data class Feature(
    val label: String
)

/**
 * 주요시설 컴포넌트
 * - 주요시설 리스트 받아오기
 * - FlowRow로 주요시설 나타내기
 * - 이미지는 그대로 쓰면 되는지?
 */

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FeatureComponent(
    features: List<Feature>,
    onClick: (Feature) -> Unit = {}
) {


    FlowRow(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        features.forEach { feature ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Gray2)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountBox,
                        contentDescription = feature.label,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = feature.label,
                        style = KUBFAndroidTheme.typography.medium13
                    )
                }
            }
        }
    }


}

@Preview
@Composable
private fun FeaturePreview() {
    val features = mutableListOf(Feature("카페"))
    features.add(Feature("편의점"))
    features.add(Feature("복사실"))
    features.add(Feature("편의점"))
    features.add(Feature("복사실"))
    features.add(Feature("편의점"))
    features.add(Feature("복사실"))
    features.add(Feature("편의점"))
    features.add(Feature("복사실"))
    FeatureComponent(features)
}