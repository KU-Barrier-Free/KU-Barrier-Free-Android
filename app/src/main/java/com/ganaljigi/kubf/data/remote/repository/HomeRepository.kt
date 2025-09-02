package com.ganaljigi.kubf.data.remote.repository

import com.ganaljigi.kubf.data.remote.response.home.HomeResponseDto
import com.ganaljigi.kubf.data.remote.response.home.HomeSearchResponseDto
import com.ganaljigi.kubf.data.remote.response.home.HomeSignificantResponseDto

interface HomeRepository {
    suspend fun getHomeData(): Result<HomeResponseDto>

    suspend fun getSpecialInfo(id: Long): Result<HomeSignificantResponseDto>

    suspend fun getHomeSearchResult(keyword: String): Result<HomeSearchResponseDto>
}