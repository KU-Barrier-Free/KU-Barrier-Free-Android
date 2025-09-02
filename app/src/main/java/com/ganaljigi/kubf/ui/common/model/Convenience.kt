package com.ganaljigi.kubf.ui.common.model

import androidx.annotation.DrawableRes
import com.ganalijigi.kubf.R

enum class Convenience(
    @DrawableRes val iconRes: Int,
    val label: String,
) {
    CONVENIENCE(
        iconRes = R.drawable.ic_convenience_store,
        label = "편의점"
    ),
    COPY(
        iconRes = R.drawable.ic_convenience_store,
        label = "복사실"
    ),
    FOYER(
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
    PARKING_LOT(
        iconRes = R.drawable.ic_convenience_store,
        label = "주차장"
    ),
    CAFE(
        iconRes = R.drawable.ic_convenience_store,
        label = "카페"
    ),
    ELEVATOR(
        iconRes = R.drawable.ic_convenience_store,
        label = "엘리베이터"
    ),
    DISABLED_TOILET(
        iconRes = R.drawable.ic_convenience_store,
        label = "장애인화장실"
    ),
    BANK(
        iconRes = R.drawable.ic_convenience_store,
        label = "은행"
    ),
    POST_OFFICE(
        iconRes = R.drawable.ic_convenience_store,
        label = "우체국"
    ),
    CULTURE(
        iconRes = R.drawable.ic_convenience_store,
        label = "문화시설"
    ),
    WELFARE_STORE(
        iconRes = R.drawable.ic_convenience_store,
        label = "복지매장"
    ),
    RESTAURANT(
        iconRes = R.drawable.ic_convenience_store,
        label = "식당"
    ),
    K_HUB(
        iconRes = R.drawable.ic_convenience_store,
        label = "K-Hub"
    ),
    ;
}

fun fromLabel(label: String): Convenience? {
    return Convenience.entries.find { it.label == label }
}

fun getIconResByName(name: String): Int {
    return Convenience.entries.find { it.name.replace("_", "").contains(name) }?.iconRes
        ?: R.drawable.ic_convenience_store
}