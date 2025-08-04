package com.ganaljigi.kubf.ui.buildinginfo.model

data class Room(
    val imageUrl: List<String>,
    val number: String,
    val name: String,
    val use: String,
    val note: List<String>
)