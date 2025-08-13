package com.ganaljigi.kubf.data.remote.repositoryimpl

import com.ganaljigi.kubf.data.remote.base.handleBaseResponse
import com.ganaljigi.kubf.data.remote.repository.BuildingRepository
import com.ganaljigi.kubf.data.remote.response.building.BuildingInfoResponseDto
import com.ganaljigi.kubf.data.remote.response.building.BuildingSummaryResponseDto
import com.ganaljigi.kubf.data.remote.service.BuildingService
import javax.inject.Inject

class BuildingRepositoryImpl @Inject constructor(
    private val buildingService: BuildingService
) : BuildingRepository {
    override suspend fun getBuildingInfo(buildingId: Long): Result<BuildingSummaryResponseDto> =
        runCatching {
            buildingService.getBuildingInfo(buildingId).handleBaseResponse().getOrThrow()
        }
}