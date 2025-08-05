package com.ganaljigi.kubf.ui.roominfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RoomInfoScreen(
    buildingName: String,
    roomPicUrls: List<String>,
    frontDoor: Boolean,
    backDoor: Boolean,
    roomNumber: String,
    roomName: String?,
    lecture: Boolean,
    capacity: Int,
    area: Double,
    floorSpace: Double,
    roomType: String,
    department: String,
    departmentNumber: String,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        RoomInfoTopAppBar(
            buildingName = buildingName,
            onBackClick = onBackClick
        )

        RoomPic(
            roomPicUrls = roomPicUrls
        )

        RoomInfoDefaultComponent(
            roomNumber = roomNumber,
            roomName = roomName,
            lecture = lecture,
            capacity = capacity,
            area = area,
            floorSpace = floorSpace,
            roomType = roomType,
            department = department,
            departmentNumber = departmentNumber
        )

        DoorComponent(
            frontDoor = frontDoor,
            backDoor = backDoor,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RoomInfoScreenPreview() {
    RoomInfoScreen(
        buildingName = "경영관",
        roomPicUrls = listOf(
            "https://i.pinimg.com/1200x/10/dc/2e/10dc2ece8b542854d0e276a1114d6190.jpg",
            "https://i.pinimg.com/1200x/d9/5e/3f/d95e3f592893bd3df9e62df37c831638.jpg"
        ),
        frontDoor = true,
        backDoor = false,
        roomNumber = "102호",
        roomName = "전산실습실",
        lecture = true,
        capacity = 34,
        area = 60.6,
        floorSpace = 18.3,
        roomType = "평탄식",
        department = "정보인프라팀",
        departmentNumber = "010-0000-0000",
        onBackClick = {}
    )
}
