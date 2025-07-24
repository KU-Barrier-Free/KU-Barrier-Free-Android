package com.ganaljigi.kubf.ui.roominfo

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomInfoTopAppBar(
    buildingName: String,
    onBackClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${buildingName} 공간",
                    style = KUBFAndroidTheme.typography.medium14.copy(
                        fontSize = 16.sp
                    )
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.fillMaxHeight()
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "뒤로가기",
                    modifier = Modifier
                        .fillMaxHeight()
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.statusBars)
    )
}

@Preview(showBackground = true)
@Composable
fun RoomInfoTopAppBarPreview() {
    KUBFAndroidTheme {
        RoomInfoTopAppBar(
            buildingName = "경영관",
            onBackClick = {}
        )
    }
}