package com.ganaljigi.kubf.ui.common.model

import androidx.annotation.DrawableRes
import com.ganalijigi.kubf.R

enum class MapToggle(
    val label: String,
    @DrawableRes val iconRes: Int
) {
    // 연석, 경사로, 게단, 특이사항
    CURB("연석", R.drawable.ic_toggle_curb),
    SLOPE("경사로", R.drawable.ic_toggle_slope),
    STAIRS("계단", R.drawable.ic_toggle_stairs),
    SPECIAL_MARK("특이사항", R.drawable.ic_toggle_special);
}