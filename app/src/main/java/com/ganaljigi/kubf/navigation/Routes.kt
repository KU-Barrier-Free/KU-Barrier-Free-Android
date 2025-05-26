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
    data object Notice : Routes

    @Serializable
    data object BuildingInfo : Routes

    @Serializable
    data object RoomInfo : Routes
}