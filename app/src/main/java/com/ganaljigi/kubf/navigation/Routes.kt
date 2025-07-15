package com.ganaljigi.kubf.navigation

import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable
    data object Splash : Routes

    @Serializable
    data object Home : Routes

    @Serializable
    data object Helper : Routes

    @Serializable
    data object Notice : Routes //만약 추가한다면 Helper->Notice url 주기

    @Serializable
    data class BuildingInfo(val number: Int) : Routes

    @Serializable
    data class RoomInfo(val number: Int) : Routes

    @Serializable
    data object Support : Routes
}