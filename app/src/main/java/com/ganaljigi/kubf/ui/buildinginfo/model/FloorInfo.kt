package com.ganaljigi.kubf.ui.buildinginfo.model

data class FloorInfo(
    val floorNum : Int = 0,
    val imageUrl: String = "",
    val facilities: List<Facility> = emptyList(),
    val rooms: List<Room> = emptyList()
)