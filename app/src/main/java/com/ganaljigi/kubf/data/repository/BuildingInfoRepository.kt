package com.ganaljigi.kubf.data.repository

import com.ganaljigi.kubf.data.remote.service.BuildingService
import com.ganaljigi.kubf.ui.buildinginfo.mapper.toBuildingInfoUi
import com.ganaljigi.kubf.ui.buildinginfo.mapper.toFacilityOrNull
import com.ganaljigi.kubf.ui.buildinginfo.mapper.toRoomUi
import com.ganaljigi.kubf.ui.buildinginfo.mapper.toUi
import com.ganaljigi.kubf.ui.buildinginfo.model.BuildingInfo
import com.ganaljigi.kubf.ui.buildinginfo.model.RoomSearchResult
import com.ganaljigi.kubf.ui.buildinginfo.model.TotalFloor
import javax.inject.Inject

interface BuildingInfoRepository {
    suspend fun fetchBuilding(id: Long): BuildingInfo
    suspend fun fetchBuildingSpaces(id: Long): Pair<BuildingInfo,TotalFloor>
    suspend fun searchSpaces(id: Long, keyword: String): List<RoomSearchResult>
}

class BuildingInfoRepositoryImpl @Inject constructor(
    private val api: BuildingService,
): BuildingInfoRepository {
    override suspend fun fetchBuilding(id: Long): BuildingInfo {
        val res = api.getBuildingInfo(id).result
        return BuildingInfo(
            id = res.id,
            name = res.name,
            number = res.number,
            lecture = res.lecture,
            facilities = res.facility.mapNotNull { it.toFacilityOrNull() },
            doors = res.doorInfos.map { it.toUi() },
            notes = emptyList(),
            latitude = res.latitude,
            longitude = res.longitude
        )
    }

    override suspend fun fetchBuildingSpaces(id: Long): Pair<BuildingInfo, TotalFloor> {
        val r = api.getBuildingSpaces(id).result
        val info = r.toBuildingInfoUi()
        val floors= r.floorList.orEmpty().map{it.toUi()}
        val total = TotalFloor(num = floors.size, floorList = floors)
        return info to total
    }

    override suspend fun searchSpaces(id: Long, keyword: String): List<RoomSearchResult> {
        if (keyword.isBlank()) return emptyList()
        val res = api.searchSpaces(id, keyword).result
        return res.spaces.map { s->
            val room = s.toRoomUi()
            RoomSearchResult(
                id= s.id,
                name = room.name.ifBlank { room.number },
                building = "",
                room = room
            )
        }
    }
}