package com.ganaljigi.kubf.ui.buildinginfo.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class Door(
    val label: String,
    val number: Int,
    val wheel: Boolean
)

/**
 * 출입문 컴포넌트 - 문 사진과 휠체어 가능 여부
 * 가로로 스크롤 가능
 */
@Composable
fun DoorComponent(doors: List<Door>) {

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(text = "출입문", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(end = 16.dp)
        ) {
            items(doors) { door ->
                DoorCard(door)

            }
        }
    }
}

/**
 * 출입문 정보 - 문 사진, 휠체어 여부
 * clickable인지? 클릭하면 상세 정보로 이동하는지 ?
 */
@Composable
fun DoorCard(door: Door) {
    Box(
        modifier = Modifier
            .width(120.dp)
            .shadow(4.dp, shape = RoundedCornerShape(12.dp), clip = false)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text( // 출입문 이름
                    text = door.label,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f), // 투명도 0.7f
                        shape = RoundedCornerShape(4.dp)
                    )
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                        .align(Alignment.TopStart)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "휠체어 진입",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if(door.wheel) "가능⭕" else "불가능❌",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                color = if (door.wheel) Color(0xFF3C8458) else Color(0xFF999999)
            )
        }
    }
}

@Preview
@Composable
private fun DoorPreview() {
    val doors = mutableListOf(Door("A-1", 1, true))
    DoorComponent(doors)
}