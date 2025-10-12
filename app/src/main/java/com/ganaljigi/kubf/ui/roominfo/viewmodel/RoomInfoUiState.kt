package com.ganaljigi.kubf.ui.roominfo.viewmodel

data class RoomInfoUiState(
    //상태 공통
    val isLoading: Boolean = false,
    val error: String? = null,

    //기본 정보 (RoomInfoDefault)
    val buildingName: String = "",
    val roomPicUrls: List<String> = emptyList(),
    val roomNumber: String = "",
    val roomName: String? = null,
    val lecture: Boolean = false,
    val capacity: Int = 0,
    val area: Double = 0.0,
    val floorSpace: Double = 0.0,
    val roomType: String = "",
    val department: String = "",
    val departmentNumber: String = "",

    //책상, 의자 정보 (DestAndChair)
    val allInOne: Boolean = false,
    val cinemaSeat: Boolean = false,
    val oneSeat: Boolean = false,
    val twoSeat: Boolean = false,
    val multiSeat: Boolean = false,
    val panel: Boolean = false,
    val backOfChair: Boolean = false,
    val wheelChair: Boolean = false,
    val wheelchairTable: Boolean = false,
    val computerTable: Boolean = false,

    //문 정보 (Door)
    val frontDoor: Boolean = false,
    val backDoor: Boolean = false,

    //API RoomInfo null 여부
    val hasRoomInfo: Boolean = true
)