package com.ganaljigi.kubf.ui.common.model

import androidx.compose.ui.text.AnnotatedString

data class SearchResult(
    val id: Long,
    val name: String,
    val building: String,
    val annotatedName: AnnotatedString,
)
