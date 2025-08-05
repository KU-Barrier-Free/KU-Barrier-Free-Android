package com.ganaljigi.kubf.data.remote.service

import com.ganaljigi.kubf.data.remote.base.BaseResponse
import com.ganaljigi.kubf.data.remote.response.home.HomeResponseDto
import com.ganaljigi.kubf.data.remote.response.home.HomeSignificantResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeService {
    @GET("home")
    suspend fun getHomeData(): BaseResponse<HomeResponseDto>

    @GET("home/outside-significants/{outsideSignificantId}")
    suspend fun getSignificantInfo(
        @Path("outsideSignificantId") id: Long
    ): BaseResponse<HomeSignificantResponseDto>
}