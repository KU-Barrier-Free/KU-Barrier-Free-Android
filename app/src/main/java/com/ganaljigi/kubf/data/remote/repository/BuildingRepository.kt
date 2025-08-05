package com.ganaljigi.kubf.data.remote.repository

import com.ganaljigi.kubf.data.remote.response.building.BuildingInfoResponseDto

interface BuildingRepository {
    suspend fun getBuildingInfo(buildingId: Long): Result<BuildingInfoResponseDto>
}