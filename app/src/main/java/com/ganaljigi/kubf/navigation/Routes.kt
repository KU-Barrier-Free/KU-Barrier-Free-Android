package com.ganaljigi.kubf.navigation

import androidx.navigation.NavType
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
    data class BuildingInfo(val number: Long) : Routes

    @Serializable
    data class RoomInfo(
        val buildingId: Long,
        val spaceId: Long,
        val type: Int = 1,
        val buildingName: String? = null
    ) : Routes

    @Serializable
    data object Support : Routes
    @Serializable
    data object DisableStudentHelper : Routes
    @Serializable
    data object JobInformation : Routes
}