package com.ganaljigi.kubf.ui.roominfo

interface RoomInfoRepository {
    suspend fun getRoomInfo(
        buildingId: Long,
        spaceId: Long,
        type: Int
    ): Result<RoomInfoResponseDto>
}