package com.ganaljigi.kubf.data.remote.service

import com.ganaljigi.kubf.data.remote.base.BaseResponse
import com.ganaljigi.kubf.data.remote.response.building.BuildingInfoResponseDto
import com.ganaljigi.kubf.data.remote.response.building.BuildingSummaryResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

import com.ganaljigi.kubf.data.remote.base.BaseResponse
import com.ganaljigi.kubf.ui.buildinginfo.response.BuildingDto
import com.ganaljigi.kubf.ui.buildinginfo.response.SearchResponseDto
import com.ganaljigi.kubf.ui.buildinginfo.response.SpacesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BuildingService {
    @GET("buildings/{buildingId}")
    suspend fun getBuildingInfo(
        @Path("buildingId") buildingId: Long
    ): BaseResponse<BuildingSummaryResponseDto>
    @GET("/buildings/{buildingId}")
    suspend fun getBuildingInfo2(@Path("buildingId") id:Long): BaseResponse<BuildingDto>

    @GET("/buildings/{buildingId}/spaces")
    suspend fun getBuildingSpaces(@Path("buildingId") id:Long): BaseResponse<SpacesDto>

    @GET("/buildings/{buildingId}/spaces/search")
    suspend fun searchSpaces(
        @Path("buildingId") id: Long,
        @Query("keyword") keyword: String
    ): BaseResponse<SearchResponseDto>
}