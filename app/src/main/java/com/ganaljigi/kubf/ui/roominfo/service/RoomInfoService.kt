package com.ganaljigi.kubf.ui.roominfo.service

import com.ganaljigi.kubf.ui.roominfo.response.RoomInfoResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RoomInfoService {
    @GET("/buildings/{buildingId}/spaces/{spaceId}")
    suspend fun getRoomInfo(
        @Path("buildingId")
        buildingId: Long,
        @Path("spaceId")
        spaceId: Long,
        @Query("type")
        type: Int // 1=강의실, 0=not강의실
    ): RoomInfoResponseDto
}