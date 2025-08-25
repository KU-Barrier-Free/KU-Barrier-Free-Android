package com.ganaljigi.kubf.ui.buildinginfo.response

data class BuildingDto(
    val id: Long,
    val number: Int,
    val name: String,
    val lecture: Boolean,
    val doorInfos: List<DoorInfoDto>,
    val facility: List<String>,
    val latitude: Double?,
    val longitude: Double?
)

data class DoorInfoDto(
    val id: Long,
    val wheel: Boolean,
    val imageUrl: List<String>,
    val latitude: Double?,
    val longitude: Double?,
    val label: String
)

data class SpacesDto(
    val id: Long,
    val number: Int,
    val name: String,
    val department: String?,
    val image: String?,
    val lecture: Boolean,
    val doorInfos: List<DoorInfoDto>,
    val facility: List<String>,
    val notes: List<NoteDto>,
    val floorList: List<FloorDto>,
    val latitude: Double?,
    val longitude: Double?
)

data class NoteDto(
    val id: Long,
    val note: String,
    val imageUrl: List<String>
)

data class FloorDto(
    val drawings: List<String>,
    val purposes: List<String>,
    val spaceSummaries: List<SpaceSummaryDto>,
    val floor: String
)

data class SpaceSummaryDto(
    val id: Long,
    val roomNumber: String?,
    val roomName: String?,
    val comment: String?,
    val roomImages: List<RoomImageDto>,
    val isLecture: Boolean
)

data class RoomImageDto(
    val imageUrl: String,
    val imageType: String // "ROOM" | "DOOR"
)

data class SearchResponseDto(
    val count: Int,
    val spaces: List<SpaceSummaryDto>
)

// 공통 응답 래퍼
data class ApiResponse<T>(
    val success: Boolean,
    val code: Int,
    val message: String,
    val result: T
)