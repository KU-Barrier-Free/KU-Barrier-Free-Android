package com.ganaljigi.kubf.ui.buildinginfo.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ganaljigi.kubf.ui.buildinginfo.model.Facility
import com.ganaljigi.kubf.ui.buildinginfo.model.FloorInfo
import com.ganaljigi.kubf.ui.buildinginfo.model.Room

@Composable
fun FloorComponent(
    current: FloorInfo,
    onRoomClick: (Room) -> Unit
) {
    Column {
        if(current.imageUrl.isNotEmpty()){
            AsyncImage(
                model = current.imageUrl.first(),
                contentDescription = "${current.floorLabel}층 사진",
                contentScale = ContentScale.Crop,
                modifier = Modifier.padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(Modifier.height(20.dp))
        }
        if (current.facilities.isNotEmpty()){
            FacilityComponent(current.facilities)
            Spacer(Modifier.height(20.dp))
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ){
            current.rooms.forEach{room->
                RoomComponent(
                    room = room,
                    onClick = {onRoomClick(room)}
                )
            }
        }
        Spacer(Modifier.height(20.dp))
    }
}

@Preview
@Composable
private fun FloorCompPreview() {
    val facilities = Facility.entries.toList()
    val urllist = mutableListOf("httpsL")
    //val rooms = mutableListOf(Room(urllist,"101", "전산실습실", "강의실", mutableListOf<String>()))
   // val floorInfos = mutableListOf(FloorInfo(1,"https://",facilities, rooms))
    //floorInfos.add(FloorInfo(2,"https://",facilities, rooms))
}
