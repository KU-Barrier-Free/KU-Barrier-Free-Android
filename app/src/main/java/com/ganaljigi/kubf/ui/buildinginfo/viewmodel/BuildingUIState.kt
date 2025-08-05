package com.ganaljigi.kubf.ui.buildinginfo.viewmodel

import com.ganaljigi.kubf.ui.buildinginfo.model.BuildingInfo
import com.ganaljigi.kubf.ui.buildinginfo.model.Door
import com.ganaljigi.kubf.ui.buildinginfo.model.Facility
import com.ganaljigi.kubf.ui.buildinginfo.model.FloorInfo
import com.ganaljigi.kubf.ui.buildinginfo.model.Note
import com.ganaljigi.kubf.ui.buildinginfo.model.Room
import com.ganaljigi.kubf.ui.buildinginfo.model.TotalFloor

data class BuildingUIState (
    val door: Door = Door(),
    val floorInfo: FloorInfo = FloorInfo(),
    val buildingInfo: BuildingInfo = BuildingInfo(),
    val note: Note? = null,
    val room: Room = Room(),
    val totalFloor: TotalFloor = TotalFloor(),
    val facility: Facility = Facility.CAFE
)