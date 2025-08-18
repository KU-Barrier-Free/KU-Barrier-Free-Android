package com.ganaljigi.kubf.ui.helper.viewmodel

data class HelperUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val notices: List<NoticeUi> = emptyList()
)

data class NoticeUi(
    val title: String,
    val date: String,
    val url: String
)