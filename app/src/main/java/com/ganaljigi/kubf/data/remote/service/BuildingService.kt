package com.ganaljigi.kubf.data.remote.service

import com.ganaljigi.kubf.ui.buildinginfo.response.ApiResponse
import com.ganaljigi.kubf.ui.buildinginfo.response.BuildingDto
import com.ganaljigi.kubf.ui.buildinginfo.response.SearchResponseDto
import com.ganaljigi.kubf.ui.buildinginfo.response.SpacesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BuildingService {
    @GET("/buildings/{id}")
    suspend fun getBuildingInfo(@Path("id") id:Long): ApiResponse<BuildingDto>

    @GET("/building/{id}/spaces")
    suspend fun getBuildingSpaces(@Path("id") id:Long): ApiResponse<SpacesDto>

    @GET("/buildings/{id}/spaces/search")
    suspend fun searchSpaces(
        @Path("id") id: Long,
        @Query("keyword") keyword: String
    ): ApiResponse<SearchResponseDto>
}