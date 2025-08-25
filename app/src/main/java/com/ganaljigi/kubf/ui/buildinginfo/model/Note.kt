package com.ganaljigi.kubf.ui.buildinginfo.model

data class Note(
    val id: Long = 0L,
    val note:String="",
    val imageUrl:List<String> = emptyList()
)