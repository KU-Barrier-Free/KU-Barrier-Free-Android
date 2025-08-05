package com.ganaljigi.kubf.data.remote.repository

import com.ganaljigi.kubf.data.remote.response.HomeResponseDto

interface HomeRepository {
    suspend fun getHomeData(): Result<HomeResponseDto>
}