package com.ganaljigi.kubf.ui.roominfo

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