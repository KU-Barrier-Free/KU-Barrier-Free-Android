package com.ganaljigi.kubf.ui.roominfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ganaljigi.kubf.ui.theme.Gray3
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.ganaljigi.kubf.ui.theme.MainGreen

@Composable
fun DoorComponent(
    doorTypes: List<String>,
    modifier: Modifier = Modifier
) {
    val allDoors = listOf("앞문", "뒷문")

    Column (

    ) {
        Divider(modifier = Modifier.padding(vertical = 16.dp))
        Text(
            text = "출입문",
            style = KUBFAndroidTheme.typography.semiBold14
        )
        Spacer(modifier = Modifier.height(8.dp))

        Column {
            allDoors.forEach { door ->
                val exists = doorTypes.contains(door)
                Row (verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = door,
                        style = if (exists)
                            KUBFAndroidTheme.typography.semiBold14.copy(color = MainGreen)
                        else
                            KUBFAndroidTheme.typography.medium14.copy(color = Gray3)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (exists) "O" else "X",
                        style = KUBFAndroidTheme.typography.medium14.copy(
                            color = if (exists) MainGreen else Gray3
                        )
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DoorComponentPreview() {
    KUBFAndroidTheme {
        DoorComponent(doorTypes = listOf("앞문"))
    }
}
