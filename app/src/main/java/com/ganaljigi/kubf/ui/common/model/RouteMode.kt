package com.ganaljigi.kubf.ui.common.model

enum class RouteMode(val label: String) {
    SHORTEST("최단 경로"),
    NO_STAIRS("계단 없는 경로"),
    BARRIER_FREE("배리어프리 경로"),
}