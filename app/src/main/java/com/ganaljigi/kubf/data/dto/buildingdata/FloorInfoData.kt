package com.ganaljigi.kubf.data.dto.buildingdata

data class FloorInfoData(
    val floorNum : Int,
    val imageUrl: String,
    val facilities: List<Facility>,
    val roomData: List<RoomData>
)