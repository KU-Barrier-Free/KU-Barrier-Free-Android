package com.ganaljigi.kubf.data.remote.response.home


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeSearchResponseDto(
    @SerialName("buildings")
    val buildings: List<Building>,
    @SerialName("facilities")
    val facilities: List<Facility>
) {
    @Serializable
    data class Building(
        @SerialName("id")
        val id: Long,
        @SerialName("latitude")
        val latitude: Double,
        @SerialName("longitude")
        val longitude: Double,
        @SerialName("name")
        val name: String
    )

    @Serializable
    data class Facility(
        @SerialName("buildingId")
        val buildingId: Long,
        @SerialName("buildingName")
        val buildingName: String,
        @SerialName("id")
        val id: Long,
        @SerialName("latitude")
        val latitude: Double,
        @SerialName("longitude")
        val longitude: Double,
        @SerialName("name")
        val name: String,
        @SerialName("purpose")
        val purpose: String
    )
}