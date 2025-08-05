package com.ganaljigi.kubf.ui.home.model

import com.ganaljigi.kubf.ui.common.model.RouteMode

data class RouteResult(
    val time: Int = 0,
    val distance: Int = 0,
    val routeMode: RouteMode = RouteMode.SHORTEST,
)

// TODO : 길찾기 노드,링크 ...