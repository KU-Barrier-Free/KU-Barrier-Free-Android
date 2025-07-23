package com.ganaljigi.kubf.ui.home.model

data class ToggleMarker(
    val id: Long,
    val name: String? = null,
    val latitude: Double,
    val longitude: Double,
    val mapToggle: MapToggle = MapToggle.CURB,
)
