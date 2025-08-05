package com.ganaljigi.kubf.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeResponseDto(
    @SerialName("buildings")
    val buildings: List<HomePin>,
    @SerialName("curbs")
    val curbs: List<HomePin>,
    @SerialName("ramps")
    val ramps: List<HomePin>,
    @SerialName("significants")
    val significants: List<HomePin>,
    @SerialName("stairs")
    val stairs: List<HomePin>
) {
    @Serializable
    data class HomePin(
        @SerialName("id")
        val id: Long,
        @SerialName("latitude")
        val latitude: Double,
        @SerialName("longitude")
        val longitude: Double
    )
}