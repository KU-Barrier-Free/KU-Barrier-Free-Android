package com.ganaljigi.kubf.navigation

import com.ganaljigi.kubf.ui.common.model.SearchMode
import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable
    data object Splash : Routes

    @Serializable
    data object Home : Routes

    @Serializable
    data class HomeSearch(val title: SearchMode) : Routes

    @Serializable
    data object Helper : Routes

    @Serializable
    data object Notice : Routes

    @Serializable
    data class BuildingInfo(val number: Int) : Routes

    @Serializable
    data class RoomInfo(val number: Int) : Routes

    @Serializable
    data object Support : Routes
}