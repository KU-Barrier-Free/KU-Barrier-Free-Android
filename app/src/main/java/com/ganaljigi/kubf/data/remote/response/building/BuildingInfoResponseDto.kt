package com.ganaljigi.kubf.data.remote.response.building


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BuildingInfoResponseDto(
    @SerialName("department")
    val department: String,
    @SerialName("doorInfos")
    val doorInfos: List<DoorInfo>,
    @SerialName("facilityPurposes")
    val facilityPurposes: List<String>,
    @SerialName("floorMap")
    val floorMap: FloorMap,
    @SerialName("id")
    val id: Long,
    @SerialName("image")
    val image: String,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("name")
    val name: String,
    @SerialName("number")
    val number: Int,
    @SerialName("significantInfos")
    val significantInfos: List<SignificantInfo>
) {
    @Serializable
    data class DoorInfo(
        @SerialName("id")
        val id: Long,
        @SerialName("imageUrl")
        val imageUrl: List<String>,
        @SerialName("latitude")
        val latitude: Double,
        @SerialName("longitude")
        val longitude: Double,
        @SerialName("wheelchair")
        val wheelchair: Boolean
    )

    @Serializable
    data class FloorMap(
        @SerialName("B1")
        val b1: B1
    ) {
        @Serializable
        data class B1(
            @SerialName("drawings")
            val drawings: List<String>,
            @SerialName("purposes")
            val purposes: List<String>,
            @SerialName("spaceSummaries")
            val spaceSummaries: List<SpaceSummary>
        ) {
            @Serializable
            data class SpaceSummary(
                @SerialName("comment")
                val comment: String,
                @SerialName("id")
                val id: Int,
                @SerialName("isLecture")
                val isLecture: Boolean,
                @SerialName("roomImages")
                val roomImages: List<RoomImage>,
                @SerialName("roomName")
                val roomName: String,
                @SerialName("roomNumber")
                val roomNumber: String
            ) {
                @Serializable
                data class RoomImage(
                    @SerialName("imageType")
                    val imageType: String,
                    @SerialName("imageUrl")
                    val imageUrl: String
                )
            }
        }
    }

    @Serializable
    data class SignificantInfo(
        @SerialName("description")
        val description: String,
        @SerialName("id")
        val id: Int,
        @SerialName("imageUrl")
        val imageUrl: List<String>
    )
}