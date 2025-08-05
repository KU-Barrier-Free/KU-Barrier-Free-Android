package com.ganaljigi.kubf.data.remote.service

import com.ganaljigi.kubf.data.remote.base.BaseResponse
import com.ganaljigi.kubf.data.remote.response.HomeResponseDto
import retrofit2.http.GET

interface HomeService {
    @GET("home")
    suspend fun getHomeData(): BaseResponse<HomeResponseDto>
}