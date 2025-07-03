package com.ganaljigi.kubf.ui.buildinginfo.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
    building: TotalBuilding,
    onRoomClick: (Room) -> Unit
) {
    var selectedIndex by remember { mutableStateOf(0) }
    val floors = building.floorList
    Column {
        TabRow(
            selectedTabIndex =  selectedIndex,
            indicator = { position ->
                TabRowDefaults.Indicator(
                    Modifier
                        .tabIndicatorOffset(position[selectedIndex])
                        .height(2.dp),
                    color = Green
                )
            },
            modifier = Modifier.fillMaxWidth()
        )  {
            floors.forEachIndexed {idx, floorInfo ->
                Tab(
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    selected = idx == selectedIndex,
                    onClick = {selectedIndex = idx},
                    text = {
                        Text(
                            text = "${floorInfo.floorNum}층",
                            textAlign = TextAlign.Center,
                            style = if (idx == selectedIndex) KUBFAndroidTheme.typography.regular14 else KUBFAndroidTheme.typography.medium14,
                            color = if (idx == selectedIndex) Green else Gray4
                        )
                    }
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        val current = floors[selectedIndex]

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(bottom = 20.dp)
        ){
            if(current.imageUrl.isNotBlank()){
                item {
                    AsyncImage(
                        model = current.imageUrl,
                        contentDescription = "${current.floorNum}층 사진",
                        contentScale = ContentScale.Crop,
                    )
                }
            }
            if (current.features.isNotEmpty()){
                item{
                    FeatureComponent(current.features)
                }
            }
            items(current.rooms){room->
                RoomComponent(
                    room = room,
                    onClick = {}
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
    //floorInfos.add(FloorInfo(3,"https://",features, rooms))
    val totalBuilding = TotalBuilding(2, floorInfos)
    FloorComponent(totalBuilding) { }
}
