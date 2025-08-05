package com.ganaljigi.kubf.dummy.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DummyResponseDto(
    @SerialName("description")
    val description: String
)