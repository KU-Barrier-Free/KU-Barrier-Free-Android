package com.ganaljigi.kubf.ui.buildinginfo.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BuildingDto(
    @SerialName("id")
    val id: Long,
    @SerialName("number")
    val number: Int,
    @SerialName("name")
    val name: String,
    @SerialName("lecture")
    val lecture: Boolean,
    @SerialName("doorInfos")
    val doorInfos: List<DoorInfoDto>,
    @SerialName("facilityPurposes")
    val facilityPurposes: List<String>,
    @SerialName("latitude")
    val latitude: Double?,
    @SerialName("longitude")
    val longitude: Double?
)

@Serializable
data class DoorInfoDto(
    @SerialName("id")
    val id: Long,
    @SerialName("wheelchair")
    val wheelchair: Boolean,
    @SerialName("imageUrl")
    val imageUrl: List<String>,
    @SerialName("latitude")
    val latitude: Double?,
    @SerialName("longitude")
    val longitude: Double?,
    @SerialName("label")
    val label: String
)

@Serializable
data class SpacesDto(
    @SerialName("id")
    val id: Long,
    @SerialName("number")
    val number: Int,
    @SerialName("name")
    val name: String,
    @SerialName("department")
    val department: String?,
    @SerialName("image")
    val image: String?,
    @SerialName("lecture")
    val lecture: Boolean,
    @SerialName("doorInfos")
    val doorInfos: List<DoorInfoDto>,
    @SerialName(value = "facilityPurposes")
    val facilityPurposes: List<String>,
    @SerialName("significantInfos")
    val significantInfos: List<SignificantInfosDto>,
    @SerialName("floorList")
    val floorList: List<FloorDto>,
    @SerialName("latitude")
    val latitude: Double?,
    @SerialName("longitude")
    val longitude: Double?
)

@Serializable
data class SignificantInfosDto(
    @SerialName("id")
    val id: Long,
    @SerialName("description")
    val description: String,
    @SerialName("imageUrl")
    val imageUrl: List<String>
)

@Serializable
data class FloorDto(
    @SerialName("drawings")
    val drawings: List<String>,
    @SerialName("purposes")
    val purposes: List<String>,
    @SerialName("spaceSummaries")
    val spaceSummaries: List<SpaceSummaryDto>,
    @SerialName("floor")
    val floor: String
)

@Serializable
data class SpaceSummaryDto(
    @SerialName("id")
    val id: Long,
    @SerialName("roomNumber")
    val roomNumber: String?,
    @SerialName("roomName")
    val roomName: String?,
    @SerialName("comment")
    val comment: String?,
    @SerialName("roomImages")
    val roomImages: List<RoomImageDto>,
    @SerialName("isLecture")
    val isLecture: Boolean
)

@Serializable
data class RoomImageDto(
    @SerialName("imageUrl")
    val imageUrl: String,
    @SerialName("imageType")
    val imageType: String // "ROOM" | "DOOR"
)

@Serializable
data class SearchResponseDto(
    @SerialName("count")
    val count: Int,
    @SerialName("spaces")
    val spaces: List<SpaceSummaryDto>
)