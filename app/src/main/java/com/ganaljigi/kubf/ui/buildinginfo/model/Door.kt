package com.ganaljigi.kubf.ui.buildinginfo.model

data class Door(
    val id: Long = 0L,
    val imageUrl: List<String> = listOf(),
    val label: String = "",
    val wheel: Boolean = false,
    val latitude: Double? = null,
    val longitude: Double? = null
)