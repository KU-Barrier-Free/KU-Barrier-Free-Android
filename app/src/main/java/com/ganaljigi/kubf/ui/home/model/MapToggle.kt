package com.ganaljigi.kubf.ui.home.model

import androidx.annotation.DrawableRes
import com.ganalijigi.kubf.R

enum class MapToggle(
    val label: String,
    @DrawableRes val toggleIconRes: Int,
    @DrawableRes val markerIconRes: Int,
) {
    // 연석, 경사로, 게단, 특이사항
    CURB("연석", R.drawable.ic_toggle_curb, R.drawable.ic_curb_marker),
    SLOPE("경사로", R.drawable.ic_toggle_slope, R.drawable.ic_slope_marker),
    STAIRS("계단", R.drawable.ic_toggle_stairs, R.drawable.ic_stairs_marker),
    SPECIAL_MARK("특이사항", R.drawable.ic_toggle_special, R.drawable.ic_special_marker), ;
}