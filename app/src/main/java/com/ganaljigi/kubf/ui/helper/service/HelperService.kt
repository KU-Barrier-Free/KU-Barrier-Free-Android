package com.ganaljigi.kubf.ui.helper.service

import com.ganaljigi.kubf.ui.helper.response.HelperNoticeResponseDto
import retrofit2.http.GET

interface HelperService {
    // /support-center -> 공지 목록 ㄱㄱ
    @GET("/support-center")
    suspend fun getSupportCenterNotices(): HelperNoticeResponseDto
}