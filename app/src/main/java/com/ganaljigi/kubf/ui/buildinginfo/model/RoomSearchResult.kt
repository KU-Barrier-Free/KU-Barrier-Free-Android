package com.ganaljigi.kubf.ui.buildinginfo.model

import androidx.annotation.DrawableRes
import com.ganalijigi.kubf.R

data class RoomSearchResult(
    val id: Long = 0L,
    val name: String = "",
    val building: String = "",
    val room: Room? = null,
    @DrawableRes val icon: Int = R.drawable.ic_toggle_curb, // TODO: Response 형식에 맞춰 수정
)
