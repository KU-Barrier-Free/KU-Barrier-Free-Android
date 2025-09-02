package com.ganaljigi.kubf.data.remote.repositoryimpl

import com.ganaljigi.kubf.data.remote.base.handleBaseResponse
import com.ganaljigi.kubf.data.remote.repository.RouteRepository
import com.ganaljigi.kubf.data.remote.response.route.PathResponseDto
import com.ganaljigi.kubf.data.remote.service.RouteService
import javax.inject.Inject

class RouteRepositoryImpl @Inject constructor(
    private val routeService: RouteService
) : RouteRepository {
    override suspend fun getPath(
        srcId: Long,
        srcType: String,
        destId: Long,
        destType: String
    ): Result<PathResponseDto> = runCatching {
        routeService.getPath(srcId, srcType, destId, destType).handleBaseResponse().getOrThrow()
    }
}