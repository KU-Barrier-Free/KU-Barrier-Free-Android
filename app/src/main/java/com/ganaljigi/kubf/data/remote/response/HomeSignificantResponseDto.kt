package com.ganaljigi.kubf.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeSignificantResponseDto(
    @SerialName("description")
    val description: String,
    @SerialName("imageUrls")
    val imageUrls: List<String>,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double
)