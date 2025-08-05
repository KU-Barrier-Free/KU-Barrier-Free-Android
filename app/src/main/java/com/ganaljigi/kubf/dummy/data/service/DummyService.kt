package com.ganaljigi.kubf.dummy.data.service

import com.ganaljigi.kubf.dummy.data.response.DummyResponseDto
import retrofit2.http.GET

interface DummyService {
    @GET("/dummy-api")
    suspend fun getDummy(): DummyResponseDto
}