package com.ganaljigi.kubf.ui.common.model

import androidx.annotation.DrawableRes
import com.ganalijigi.kubf.R

enum class Convenience(
    @DrawableRes val iconRes: Int,
    val label: String,
) {
    CONVENIENCE_STORE(
        iconRes = R.drawable.ic_convenience_store,
        label = "편의점"
    ),
    COPY_ROOM(
        iconRes = R.drawable.ic_convenience_store,
        label = "복사실"
    ),
    LOUNGE(
        iconRes = R.drawable.ic_convenience_store,
        label = "휴게실"
    ),
    K_CUBE(
        iconRes = R.drawable.ic_convenience_store,
        label = "K-CUBE"
    ),
    IT_SERVICE_CENTER(
        iconRes = R.drawable.ic_convenience_store,
        label = "IT-서비스센터"
    ),
    PARKING(
        iconRes = R.drawable.ic_convenience_store,
        label = "주차장"
    ),
    CAFE(
        iconRes = R.drawable.ic_convenience_store,
        label = "카페"
    ),
}