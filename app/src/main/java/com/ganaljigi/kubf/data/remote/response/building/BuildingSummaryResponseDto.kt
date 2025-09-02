package com.ganaljigi.kubf.data.remote.response.building


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BuildingSummaryResponseDto(
    @SerialName("doorInfos")
    val doorInfos: List<DoorInfoDto>,
    @SerialName("facilityPurposes")
    val facilityPurposes: List<String>,
    @SerialName("id")
    val id: Long,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("lecture")
    val lecture: Boolean,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("name")
    val name: String,
    @SerialName("number")
    val number: Int
) {
    @Serializable
    data class DoorInfoDto(
        @SerialName("id")
        val id: Long,
        @SerialName("imageUrl")
        val imageUrl: List<String>,
        @SerialName("label")
        val label: String,
        @SerialName("latitude")
        val latitude: Double,
        @SerialName("longitude")
        val longitude: Double,
        @SerialName("wheelchair")
        val wheelchair: Boolean
    )
}