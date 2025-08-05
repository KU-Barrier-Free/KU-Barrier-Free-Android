package com.ganaljigi.kubf.ui.buildinginfo.model

data class BuildingInfo(
    val name: String = "",
    val number: Int = 0,
    val department: String = "",
    val imageUrl: String = "",
    val notes: List<Note> = emptyList(),
    val facilities: List<Facility> = emptyList(),
    val doors:List<Door> = emptyList()
)
