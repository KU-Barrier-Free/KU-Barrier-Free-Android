package com.ganaljigi.kubf.ui.common.model

data class DoorInfo(
    val id: Long = 0L,
    val label: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val imageUrls: List<String> = emptyList(),
    val isWheelchairAccessible: Boolean = false,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
)
