package com.ganaljigi.kubf.ui.buildinginfo.model

data class Room(
    val id:Long = 0L,
    val imageUrl: List<String> = emptyList(),
    val number: String = "",
    val name: String="",
    val isLecture: Boolean = false,
    val note: List<String> = emptyList()
)