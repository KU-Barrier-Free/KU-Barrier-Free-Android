package com.ganaljigi.kubf.dummy.data.repository

import com.ganaljigi.kubf.dummy.data.response.DummyResponseDto

interface DummyRepository {
    suspend fun dummy(): Result<DummyResponseDto>
}