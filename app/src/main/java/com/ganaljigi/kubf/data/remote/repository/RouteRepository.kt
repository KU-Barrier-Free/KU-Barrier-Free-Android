package com.ganaljigi.kubf.data.remote.repository

import com.ganaljigi.kubf.data.remote.response.route.PathResponseDto

interface RouteRepository {
    suspend fun getPath(
        srcId: Long,
        srcType: String,
        destId: Long,
        destType: String
    ): Result<PathResponseDto>
}