package com.ganaljigi.kubf.data.remote.repository

import com.ganaljigi.kubf.data.remote.response.HomeResponseDto
import com.ganaljigi.kubf.data.remote.response.HomeSignificantResponseDto

interface HomeRepository {
    suspend fun getHomeData(): Result<HomeResponseDto>

    suspend fun getSpecialInfo(id: Long): Result<HomeSignificantResponseDto>
}