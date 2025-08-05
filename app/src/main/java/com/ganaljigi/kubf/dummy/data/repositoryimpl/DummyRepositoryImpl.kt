package com.ganaljigi.kubf.dummy.data.repositoryimpl

import com.ganaljigi.kubf.dummy.data.response.DummyResponseDto
import com.ganaljigi.kubf.dummy.data.repository.DummyRepository
import com.ganaljigi.kubf.dummy.data.service.DummyService
import javax.inject.Inject

class DummyRepositoryImpl @Inject constructor(
    private val dummyService: DummyService
) : DummyRepository {
    override suspend fun dummy(): Result<DummyResponseDto> = runCatching {
        dummyService.getDummy()
    }
}