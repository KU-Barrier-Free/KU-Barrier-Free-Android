package com.ganaljigi.kubf.ui.roominfo.repositoryimpl

import com.ganaljigi.kubf.ui.roominfo.response.RoomInfoResponseDto
import com.ganaljigi.kubf.ui.roominfo.service.RoomInfoService
import com.ganaljigi.kubf.ui.roominfo.repository.RoomInfoRepository
import javax.inject.Inject

class RoomInfoRepositoryImpl @Inject constructor(
    private val service: RoomInfoService
) : RoomInfoRepository {
    override suspend fun getRoomInfo(
        buildingId: Long,
        spaceId: Long,
        type: Int
    ): Result<RoomInfoResponseDto> = runCatching {
        service.getRoomInfo(buildingId, spaceId, type)
    }
}