package com.ganaljigi.kubf.data.dto.buildingdata

import com.ganaljigi.kubf.ui.buildinginfo.component.Facility

data class FloorInfoData(
    val floorNum : Int,
    val imageUrl: String,
    val facilities: List<Facility>,
    val roomData: List<RoomData>
)