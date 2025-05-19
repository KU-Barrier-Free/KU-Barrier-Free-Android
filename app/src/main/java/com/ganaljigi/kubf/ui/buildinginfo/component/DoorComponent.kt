package com.ganaljigi.kubf.ui.buildinginfo.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Composable
fun DoorComponent(doors:List<Door>) {
    Text(text = "출입믄", style = MaterialTheme.typography.labelLarge)
    Spacer(modifier = Modifier.height(5.dp))
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        doors.forEach { door ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(80.dp)
            ) {
//                Image(
//                    painter = ,
//                    contentDescription = "${door.label} 출입문"
//
//                )
                Text(
                    text = if (door.wheel) "가능" else "불가능",
                    color = if (door.wheel) Color.Green else Color.Red,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview
@Composable
private fun DoorPreview() {
    val doors = mutableListOf(Door("창의관", 1, true))
    DoorComponent(doors)
}