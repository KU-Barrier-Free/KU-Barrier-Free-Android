package com.ganaljigi.kubf.ui.roominfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ganaljigi.kubf.ui.roominfo.viewmodel.RoomInfoViewModel

@Composable
fun RoomInfoScreen(
    onBackClick: () -> Unit,
    viewModel: RoomInfoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .verticalScroll(scrollState)
    ) {
        RoomInfoTopAppBar(
            buildingName = uiState.buildingName,
            onBackClick = onBackClick
        )

        RoomPic(
            roomPicUrls = uiState.roomPicUrls
        )

        RoomInfoDefaultComponent(
            roomNumber = uiState.roomNumber,
            roomName = uiState.roomName,
            lecture = uiState.lecture,
            capacity = uiState.capacity,
            area = uiState.area,
            floorSpace = uiState.floorSpace,
            roomType = uiState.roomType,
            department = uiState.department,
            departmentNumber = uiState.departmentNumber
        )

        DeskAndChairComponent(
            allInOne = uiState.allInOne,
            cinemaSeat = uiState.cinemaSeat,
            oneSeat = uiState.oneSeat,
            twoSeat = uiState.twoSeat,
            multiSeat = uiState.multiSeat,
            panel = uiState.panel,
            backOfChair = uiState.backOfChair,
            wheelChair = uiState.wheelChair,
            wheelchairTable = uiState.wheelchairTable,
            computerTable = uiState.computerTable,
            modifier = Modifier.fillMaxWidth()
        )

        DoorComponent(
            frontDoor = uiState.frontDoor,
            backDoor = uiState.backDoor,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun RoomInfoScreenPreview(
// viewModel: RoomInfoViewModel = hiltViewModel()) {
//    RoomInfoScreen(
//        buildingName = "경영관",
//        roomPicUrls = listOf(
//            "https://i.pinimg.com/1200x/10/dc/2e/10dc2ece8b542854d0e276a1114d6190.jpg",
//            "https://i.pinimg.com/1200x/d9/5e/3f/d95e3f592893bd3df9e62df37c831638.jpg"
//        ),
//        frontDoor = true,
//        backDoor = false,
//        roomNumber = "102호",
//        roomName = "전산실습실",
//        lecture = true,
//        capacity = 34,
//        area = 60.6,
//        floorSpace = 18.3,
//        roomType = "평탄식",
//        department = "정보인프라팀",
//        departmentNumber = "010-0000-0000",
//
//        allInOne = false,
//        cinemaSeat = false,
//        oneSeat = true,
//        twoSeat = false,
//        multiSeat = false,
//        panel = true,
//        backOfChair = true,
//        wheelChair = true,
//        wheelchairTable = false,
//        computerTable = false,
//
//        onBackClick = {}
//    )
//}
