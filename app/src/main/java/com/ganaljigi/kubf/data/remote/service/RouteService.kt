package com.ganaljigi.kubf.data.remote.service

import com.ganaljigi.kubf.data.remote.base.BaseResponse
import com.ganaljigi.kubf.data.remote.response.route.PathResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RouteService {
    @GET("path")
    suspend fun getPath(
        @Query("srcId") srcId: Long,
        @Query("srcType") srcType: String,
        @Query("destId") destId: Long,
        @Query("destType") destType: String
    ): BaseResponse<PathResponseDto>
}