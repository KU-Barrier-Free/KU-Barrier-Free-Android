package com.ganaljigi.kubf.ui.helper.repositoryimpl

import com.ganaljigi.kubf.ui.helper.repository.HelperRepository
import com.ganaljigi.kubf.ui.helper.response.HelperNoticeResponseDto
import com.ganaljigi.kubf.ui.helper.service.HelperService
import javax.inject.Inject

class HelperRepositoryImpl @Inject constructor(
    private val service: HelperService
) : HelperRepository {
    override suspend fun getHelper(
        title: String,
        date: String,
        url: String
    ): Result<HelperNoticeResponseDto> = runCatching {
        service.getSupportCenterNotices()
    }
}