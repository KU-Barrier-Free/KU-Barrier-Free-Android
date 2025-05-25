package com.ganaljigi.kubf.ui.buildinginfo.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class Room(
    val number:String,
    val name : String,
    val use:String,
    val note: String

)

@Composable
fun RoomComponent(
    room: Room,
    onClick: ()->Unit = {}
) {
    Box(){
        Column {
            Row {
                Text(
                    text = "${room.number}호 ${room.name}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(Modifier.width(8.dp))

            }
        }
    }
}

@Preview
@Composable
private fun PreviewRoom() {
    val room = Room("101","전산실습실","강의실","경사로")
    RoomComponent(room)
}