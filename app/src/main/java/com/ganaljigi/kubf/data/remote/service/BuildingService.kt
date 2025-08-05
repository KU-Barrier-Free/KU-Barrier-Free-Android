package com.ganaljigi.kubf.data.remote.service

import com.ganaljigi.kubf.data.remote.base.BaseResponse
import com.ganaljigi.kubf.data.remote.response.building.BuildingInfoResponseDto
import retrofit2.http.GET

interface BuildingService {
    @GET("buildings/{buildingId}")
    suspend fun getBuildingInfo(
        buildingId: Long
    ): BaseResponse<BuildingInfoResponseDto>
}