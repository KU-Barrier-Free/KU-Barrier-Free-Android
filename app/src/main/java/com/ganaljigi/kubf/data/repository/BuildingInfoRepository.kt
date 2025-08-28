package com.ganaljigi.kubf.data.repository

import com.ganaljigi.kubf.ui.buildinginfo.model.BuildingInfo
import com.ganaljigi.kubf.ui.buildinginfo.model.RoomSearchResult
import com.ganaljigi.kubf.ui.buildinginfo.model.TotalFloor

interface BuildingInfoRepository {
    suspend fun fetchBuilding(id: Long): BuildingInfo
    suspend fun fetchBuildingSpaces(id: Long): Pair<BuildingInfo,TotalFloor>
    suspend fun searchSpaces(id: Long, keyword: String): List<RoomSearchResult>
}

