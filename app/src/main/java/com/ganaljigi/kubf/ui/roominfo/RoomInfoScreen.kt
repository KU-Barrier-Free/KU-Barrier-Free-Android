package com.ganaljigi.kubf.ui.roominfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ganaljigi.kubf.ui.roominfo.component.DeskAndChairComponent
import com.ganaljigi.kubf.ui.roominfo.component.DoorComponent
import com.ganaljigi.kubf.ui.roominfo.component.RoomInfoDefaultComponent
import com.ganaljigi.kubf.ui.roominfo.component.RoomInfoTopAppBar
import com.ganaljigi.kubf.ui.roominfo.component.RoomPic
import com.ganaljigi.kubf.ui.roominfo.viewmodel.RoomInfoUiState
import com.ganaljigi.kubf.ui.roominfo.viewmodel.RoomInfoViewModel

// 2-1) uiState를 직접 받는 버전 (프리뷰/에뮬에 더미 주입용)
@Composable
fun RoomInfoScreen(
    uiState: RoomInfoUiState,
    onBackClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            RoomInfoTopAppBar(
                buildingName = uiState.buildingName,
                onBackClick = onBackClick
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            if (uiState.roomPicUrls.isNotEmpty()) {
                RoomPic(roomPicUrls = uiState.roomPicUrls)
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.LightGray)
                ) {
                    Text(
                        text = "사진 없음",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

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
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

// 2-2) 기존 뷰모델 버전은 위로 위임 (그대로 두고 수정)
@Composable
fun RoomInfoScreen(
    onBackClick: () -> Unit,
    viewModel: RoomInfoViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    RoomInfoScreen(uiState = uiState, onBackClick = onBackClick)
}


@Composable
fun RoomInfoScreenDummy(
    uiState: RoomInfoUiState,
    onBackClick: () -> Unit,
) {
    RoomInfoScreen(uiState = uiState, onBackClick = onBackClick)
}

@Preview(showBackground = true)
@Composable
fun RoomInfoScreenPreview() {
    RoomInfoScreen(
        uiState = RoomInfoUiState(
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
            allInOne = false,
            cinemaSeat = false,
            oneSeat = true,
            twoSeat = false,
            multiSeat = false,
            panel = true,
            backOfChair = true,
            wheelChair = true,
            wheelchairTable = false,
            computerTable = false
        ),
        onBackClick = {}
    )
}