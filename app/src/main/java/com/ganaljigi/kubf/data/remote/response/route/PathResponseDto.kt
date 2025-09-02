package com.ganaljigi.kubf.data.remote.response.route

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class PathResponseDto(
    @SerialName("shortestPath") val shortestPath: RoutePathDto? = null,
    @SerialName("noStairsPath") val noStairsPath: RoutePathDto? = null,
    @SerialName("barrierFreePath") val barrierFreePath: RoutePathDto? = null
)

@Serializable
data class RoutePathDto(
    @SerialName("type") val type: String = "",
    @SerialName("features") val features: List<RouteFeatureDto> = emptyList(),
    @SerialName("totalDistance") val totalDistance: String = "0m"
)

@Serializable
data class RouteFeatureDto(
    @SerialName("type") val type: String = "",
    @SerialName("geometry") val geometry: RouteGeometryDto? = null,
    @SerialName("properties") val properties: RoutePropertiesDto? = null
)

@Serializable
data class RouteGeometryDto(
    @SerialName("type") val type: String = "",
    @SerialName("coordinates") val coordinates: JsonElement
)

@Serializable
data class RoutePropertiesDto(
    @SerialName("type") val type: String = "",
    @SerialName("index") val index: Int = 0
)