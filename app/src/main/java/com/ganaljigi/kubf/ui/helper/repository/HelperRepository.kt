package com.ganaljigi.kubf.ui.helper.repository

import com.ganaljigi.kubf.ui.helper.response.HelperNoticeResponseDto

interface HelperRepository {
    suspend fun getHelper(
        title: String,
        date: String,
        url: String
    ): Result<HelperNoticeResponseDto>
}