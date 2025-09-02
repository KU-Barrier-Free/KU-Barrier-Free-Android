package com.ganaljigi.kubf.data.remote.service

import com.ganaljigi.kubf.data.remote.base.BaseResponse
import com.ganaljigi.kubf.data.remote.response.home.HomeResponseDto
import com.ganaljigi.kubf.data.remote.response.home.HomeSearchResponseDto
import com.ganaljigi.kubf.data.remote.response.home.HomeSignificantResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeService {
    @GET("home")
    suspend fun getHomeData(): BaseResponse<HomeResponseDto>

    @GET("home/outside-significants/{outsideSignificantId}")
    suspend fun getSignificantInfo(
        @Path("outsideSignificantId") id: Long
    ): BaseResponse<HomeSignificantResponseDto>

    @GET("places/search")
    suspend fun getHomeSearchResult(
        @Query("keyword") keyword: String,
    ): BaseResponse<HomeSearchResponseDto>
}