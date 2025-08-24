package com.ganaljigi.kubf.ui.helper.repository

import com.ganaljigi.kubf.ui.helper.response.HelperNoticeResponseDto

interface HelperRepository {
    suspend fun fetchNotices(): Result<HelperNoticeResponseDto>
}