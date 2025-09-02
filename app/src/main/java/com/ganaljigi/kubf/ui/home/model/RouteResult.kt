package com.ganaljigi.kubf.ui.home.model

import com.ganaljigi.kubf.ui.common.model.RouteMode
import com.google.android.gms.maps.model.LatLng
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class RouteResult(
    val time: Int = 0,
    val distance: Int = 0,
    val routeMode: RouteMode = RouteMode.SHORTEST,
    val pathPoints: ImmutableList<LatLng> = persistentListOf(),
    val distanceText: String = "",
)

data class RoutePoint(
    val latitude: Double,
    val longitude: Double,
    val type: String,
    val index: Int
)

data class RouteLine(
    val points: List<LatLng>,
    val type: String,
    val index: Int
)