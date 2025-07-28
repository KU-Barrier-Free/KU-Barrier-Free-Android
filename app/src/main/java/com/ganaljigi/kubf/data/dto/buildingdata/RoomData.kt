package com.ganaljigi.kubf.data.dto.buildingdata

data class RoomData(
    val imageUrl: List<String>,
    val number: String,
    val name: String,
    val use: String,
    val note: List<String>
)