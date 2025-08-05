package com.ganaljigi.kubf.dummy.mapper

import com.ganaljigi.kubf.dummy.data.response.DummyResponseDto
import com.ganaljigi.kubf.dummy.ui.dummy.model.DummyUiState

fun DummyResponseDto.toUiState() = DummyUiState(
    description = this.description,
)