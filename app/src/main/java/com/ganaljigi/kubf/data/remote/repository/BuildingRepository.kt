package com.ganaljigi.kubf.data.remote.repository

import com.ganaljigi.kubf.data.remote.response.building.BuildingSummaryResponseDto

interface BuildingRepository {
    suspend fun getBuildingInfo(buildingId: Long): Result<BuildingSummaryResponseDto>
}