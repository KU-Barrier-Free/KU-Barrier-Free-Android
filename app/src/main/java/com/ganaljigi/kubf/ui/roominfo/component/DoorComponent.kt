package com.ganaljigi.kubf.ui.roominfo.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ganaljigi.kubf.ui.theme.Gray1
import com.ganaljigi.kubf.ui.theme.Gray3
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import com.ganaljigi.kubf.ui.theme.MainGreen

@Composable
fun DoorComponent(
    frontDoor: Boolean,
    backDoor: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(start = 20.dp)) {
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            color = Gray1,
            thickness = 1.dp
        )

        Text(
            text = "출입문",
            style = KUBFAndroidTheme.typography.semiBold16
        )

        Spacer(modifier = Modifier.height(12.dp))

        DoorItem(label = "앞문", exists = frontDoor)

        Spacer(modifier = Modifier.height(20.dp))
        DoorItem(label = "뒷문", exists = backDoor)
    }
}

@Composable
fun DoorItem(
    label: String,
    exists: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = if (exists)
                KUBFAndroidTheme.typography.semiBold16.copy(color = MainGreen)
            else
                KUBFAndroidTheme.typography.regular14.copy(color = Gray3)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = if (exists) "O" else "X",
            style = if (exists)
                KUBFAndroidTheme.typography.semiBold16.copy(color = MainGreen)
            else
                KUBFAndroidTheme.typography.regular14.copy(color = Gray3)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DoorComponentPreview() {
    KUBFAndroidTheme {
        DoorComponent(frontDoor = true, backDoor = false)
    }
}
