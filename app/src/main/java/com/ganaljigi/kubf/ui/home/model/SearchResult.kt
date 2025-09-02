package com.ganaljigi.kubf.ui.home.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.text.AnnotatedString
import com.ganalijigi.kubf.R

data class SearchResult(
    val id: Long = 0L,
    val name: String = "",
    val building: String = "",
    val searchKeyword: String = "",
    val isBuilding: Boolean = false,
    @DrawableRes val icon: Int = R.drawable.ic_toggle_curb, // TODO: Response 형식에 맞춰 수정
)
