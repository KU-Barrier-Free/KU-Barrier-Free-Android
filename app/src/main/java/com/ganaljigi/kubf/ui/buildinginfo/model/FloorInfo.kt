package com.ganaljigi.kubf.ui.buildinginfo.model

data class FloorInfo(
    val floorNum : Int,
    val imageUrl: String,
    val facilities: List<Facility>,
    val rooms: List<Room>
)