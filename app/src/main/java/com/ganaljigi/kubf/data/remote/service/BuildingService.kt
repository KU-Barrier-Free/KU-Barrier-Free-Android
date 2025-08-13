package com.ganaljigi.kubf.data.remote.service

import com.ganaljigi.kubf.data.remote.base.BaseResponse
import com.ganaljigi.kubf.data.remote.response.building.BuildingInfoResponseDto
import com.ganaljigi.kubf.data.remote.response.building.BuildingSummaryResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface BuildingService {
    @GET("buildings/{buildingId}")
    suspend fun getBuildingInfo(
        @Path("buildingId") buildingId: Long
    ): BaseResponse<BuildingSummaryResponseDto>
}