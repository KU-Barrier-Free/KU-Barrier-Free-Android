package com.ganaljigi.kubf.ui.roominfo.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoomInfoResponseDto(
    @SerialName("success")
    val success: Boolean,
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: RoomInfoResultDto? = null
)

@Serializable
data class RoomInfoResultDto(
    @SerialName("roomNumber")
    val roomNumber: String,
    @SerialName("roomName")
    val roomName: String? = null,
    @SerialName("lecture")
    val lecture: Boolean,
    @SerialName("capacity")
    val capacity: Int,
    @SerialName("area")
    val area: Double,
    @SerialName("roomComment")
    val roomComment: String? = null,
    @SerialName("floorSpace")
    val floorSpace: Double,
    @SerialName("roomType")
    val roomType: String,
    @SerialName("department")
    val department: String,
    @SerialName("departmentNumber")
    val departmentNumber: String,
    @SerialName("roomInfo")
    val roomInfo: RoomInfoDetailDto? = null, // lecture=false면 null(강의실 아니면 기본 정보만 있음)
    @SerialName("images")
    val images: List<ImageDto> = emptyList()
)

@Serializable
data class RoomInfoDetailDto(
    @SerialName("allInOne")
    val allInOne: Boolean,
    @SerialName("cinemaSeat")
    val cinemaSeat: Boolean,
    @SerialName("oneSeat")
    val oneSeat: Boolean,
    @SerialName("twoSeat")
    val twoSeat: Boolean,
    @SerialName("multiSeat")
    val multiSeat: Boolean,
    @SerialName("panel")
    val panel: Boolean,
    @SerialName("backOfChair")
    val backOfChair: Boolean,
    @SerialName("wheelchairTable")
    val wheelchairTable: Boolean,
    @SerialName("wheelChair")
    val wheelChair: Boolean,
    @SerialName("computerTable")
    val computerTable: Boolean,
    @SerialName("frontDoor")
    val frontDoor: Boolean,
    @SerialName("backDoor")
    val backDoor: Boolean
)

@Serializable
data class ImageDto(
    @SerialName("imageUrl")
    val imageUrl: String,
    @SerialName("imageType")
    val imageType: String // "ROOM" | "DOOR"
)