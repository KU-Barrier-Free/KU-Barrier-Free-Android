package com.ganaljigi.kubf.ui.common.model

import androidx.annotation.DrawableRes
import com.ganalijigi.kubf.R

data class MapMarker(
    val id: Long,
    val name: String? = null,
    val latitude: Double,
    val longitude: Double,
    @DrawableRes val iconRes: Int = R.drawable.ic_toggle_curb,
)
