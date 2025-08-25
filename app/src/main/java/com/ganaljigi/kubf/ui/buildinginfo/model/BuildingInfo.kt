package com.ganaljigi.kubf.ui.buildinginfo.model

data class BuildingInfo(
    val id: Long = 0L,
    val name: String = "",
    val number: Int = 0,
    val department: String = "",
    val imageUrl: String = "",
    val lecture: Boolean = false,
    val notes: List<Note> = emptyList(),
    val facilities: List<Facility> = emptyList(),
    val doors:List<Door> = emptyList(),
    val latitude: Double? = null,
    val longitude: Double? = null
)
