package com.ganaljigi.kubf.ui.roominfo.viewmodel

data class RoomInfoUiState(
    val buildingName: String = "",
    val roomPicUrls: List<String> = emptyList(),
    val roomNumber: String = "",
    val roomName: String? = "",
    val lecture: Boolean = true,
    val capacity: Int = 0,
    val area: Double = 0.0,
    val floorSpace: Double = 0.0,
    val roomType: String = "",
    val department: String = "",
    val departmentNumber: String = "",

    val allInOne: Boolean = true,
    val cinemaSeat: Boolean = true,
    val oneSeat: Boolean = true,
    val twoSeat: Boolean = true,
    val multiSeat: Boolean = true,
    val panel: Boolean = true,
    val backOfChair: Boolean = true,
    val wheelChair: Boolean = true,
    val wheelchairTable: Boolean = true,
    val computerTable: Boolean = true,

    val frontDoor: Boolean = true,
    val backDoor: Boolean = true,
)