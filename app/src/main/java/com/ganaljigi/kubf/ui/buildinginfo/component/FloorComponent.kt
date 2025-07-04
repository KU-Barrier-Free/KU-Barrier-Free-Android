package com.ganaljigi.kubf.ui.buildinginfo.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ganaljigi.kubf.ui.theme.Gray4
import com.ganaljigi.kubf.ui.theme.Green
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme

data class FloorInfo(
    val floorNum : Int,
    val imageUrl: String,
    val features: List<Feature>,
    val rooms: List<Room>
)

data class TotalBuilding(
    val num : Int,
    val floorList: List<FloorInfo>
)

@Composable
fun FloorComponent(
    current: FloorInfo,
    onRoomClick: (Room) -> Unit
) {
    Column {
        if(current.imageUrl.isNotBlank()){
            AsyncImage(
                model = current.imageUrl,
                contentDescription = "${current.floorNum}층 사진",
                contentScale = ContentScale.Crop,
            )
            Spacer(Modifier.height(20.dp))
        }
        if (current.features.isNotEmpty()){
            FeatureComponent(current.features)
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
                    onClick = {onRoomClick}
                )
            }
        }
    }
}

@Preview
@Composable
private fun FloorCompPreview() {
    val features = mutableListOf(Feature("카페"))
    features.add(Feature("휴게실"))
    val urllist = mutableListOf("httpsL")
    val rooms = mutableListOf(Room(urllist,"101", "전산실습실", "강의실", mutableListOf<String>()))
    val floorInfos = mutableListOf(FloorInfo(1,"https://",features, rooms))
    floorInfos.add(FloorInfo(2,"https://",features, rooms))

}
