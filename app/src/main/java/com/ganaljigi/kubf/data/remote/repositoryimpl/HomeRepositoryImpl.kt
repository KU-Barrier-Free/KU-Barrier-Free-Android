package com.ganaljigi.kubf.data.remote.repositoryimpl

import com.ganaljigi.kubf.data.remote.base.handleBaseResponse
import com.ganaljigi.kubf.data.remote.repository.HomeRepository
import com.ganaljigi.kubf.data.remote.response.home.HomeResponseDto
import com.ganaljigi.kubf.data.remote.response.home.HomeSignificantResponseDto
import com.ganaljigi.kubf.data.remote.service.HomeService
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeService: HomeService
) : HomeRepository {
    override suspend fun getHomeData(): Result<HomeResponseDto> = runCatching {
        homeService.getHomeData().handleBaseResponse().getOrThrow()
    }

    override suspend fun getSpecialInfo(id: Long): Result<HomeSignificantResponseDto> =
        runCatching {
            homeService.getSignificantInfo(id).handleBaseResponse().getOrThrow()
        }
}