package com.ganaljigi.kubf.ui.roominfo.repository

import com.ganaljigi.kubf.ui.roominfo.response.RoomInfoResponseDto

interface RoomInfoRepository {
    suspend fun getRoomInfo(
        buildingId: Long,
        spaceId: Long,
        type: Int
    ): Result<RoomInfoResponseDto>
}