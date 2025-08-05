package com.ganaljigi.kubf.ui.buildinginfo.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ganalijigi.kubf.R
import com.ganaljigi.kubf.ui.buildinginfo.component.DoorComponent
import com.ganaljigi.kubf.ui.buildinginfo.component.FacilityComponent
import com.ganaljigi.kubf.ui.buildinginfo.component.FloorComponent
import com.ganaljigi.kubf.ui.buildinginfo.component.NoteComponent
import com.ganaljigi.kubf.ui.buildinginfo.component.SearchPopup
import com.ganaljigi.kubf.ui.buildinginfo.model.BuildingInfo
import com.ganaljigi.kubf.ui.buildinginfo.model.Door
import com.ganaljigi.kubf.ui.buildinginfo.model.Facility
import com.ganaljigi.kubf.ui.buildinginfo.model.FloorInfo
import com.ganaljigi.kubf.ui.buildinginfo.model.Notes
import com.ganaljigi.kubf.ui.buildinginfo.model.Room
import com.ganaljigi.kubf.ui.buildinginfo.model.TotalFloor
import com.ganaljigi.kubf.ui.theme.Gray3
import com.ganaljigi.kubf.ui.theme.Gray4
import com.ganaljigi.kubf.ui.theme.MainGreen
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun BuildingInfoScreen(
    building: BuildingInfo,
    facilities: List<Facility>,
    doors: List<Door>,
    totalFloor: TotalFloor,
    onBack: () -> Unit,
    onSearch: () -> Unit,
    onDoorClick: (Door) -> Unit
) {
    var selectedIndex by remember { mutableStateOf(0) }
    val floors = totalFloor.floorList
    val current = floors[selectedIndex]
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    var showSearchPopup by remember { mutableStateOf(false) }
    var searchValue by remember { mutableStateOf(TextFieldValue()) }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "뒤로")
                    }
                },
                title = {
                    Text(
                        text = building.name,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = KUBFAndroidTheme.typography.medium15.copy(
                            fontSize = 16.sp
                        )
                    )
                },
                actions = {
                    IconButton(onClick = {
                        showSearchPopup = true
                        onSearch()
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_search_bar_leading),
                            contentDescription = "검색"
                        )
                    }
                }
            )
        }
    ) { inner ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
        ) {
            item {
                // 건물 이미지
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    AsyncImage(
                        model = building.imageUrl,
                        contentDescription = "${building.name} 이미지",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .background(color = Color.LightGray)
                            .fillMaxWidth()
                    )
                }
                Spacer(Modifier.height(16.dp))

                // 건물 이름, 번호
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = building.name,
                        style = KUBFAndroidTheme.typography.bold18
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "건물번호: ${building.number}",
                        style = KUBFAndroidTheme.typography.regular14,
                        color = Gray3
                    )
                }
                Spacer(Modifier.height(24.dp))
                Text(
                    text = "소속 부서",
                    style = KUBFAndroidTheme.typography.semiBold16,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = building.department,
                    style = KUBFAndroidTheme.typography.regular14,
                    color = Gray4,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(20.dp))
                Text(
                    text = "주요시설",
                    style = KUBFAndroidTheme.typography.semiBold16,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                FacilityComponent(
                    facilities = facilities
                )
                Spacer(Modifier.height(20.dp))
                Text(
                    text = "출입문",
                    style = KUBFAndroidTheme.typography.semiBold16,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                DoorComponent(doors = doors)
                Spacer(Modifier.height(20.dp))
                if (building.notes.note.isNotBlank()) {
                    Text(
                        text = "특이사항",
                        style = KUBFAndroidTheme.typography.semiBold16,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    NoteComponent(note = building.notes)
                    Spacer(Modifier.height(20.dp))
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "층별 정보",
                    style = KUBFAndroidTheme.typography.semiBold16,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(12.dp))
            }
            stickyHeader {
                if (totalFloor.num < 8) {
                    TabRow(
                        selectedTabIndex = selectedIndex,
                        indicator = { position ->
                            TabRowDefaults.Indicator(
                                Modifier
                                    .tabIndicatorOffset(position[selectedIndex])
                                    .height(2.dp),
                                color = MainGreen
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        floors.forEachIndexed { idx, floorInfo ->
                            Tab(
                                selected = idx == selectedIndex,
                                onClick = {
                                    selectedIndex = idx
                                    scope.launch {
                                        //listState.animateScrollToItem(1)
                                        listState.scrollToItem(1)
                                    }
                                },
                                text = {
                                    Text(
                                        text = "${floorInfo.floorNum}층",
                                        textAlign = TextAlign.Center,
                                        style = if (idx == selectedIndex) KUBFAndroidTheme.typography.regular14 else KUBFAndroidTheme.typography.medium14,
                                        color = if (idx == selectedIndex) MainGreen else Gray4
                                    )
                                }
                            )
                        }
                    }
                } else {
                    ScrollableTabRow(
                        selectedTabIndex = selectedIndex,
                        indicator = { position ->
                            TabRowDefaults.Indicator(
                                Modifier
                                    .tabIndicatorOffset(position[selectedIndex])
                                    .height(2.dp),
                                color = MainGreen
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        edgePadding = 0.dp
                    ) {
                        floors.forEachIndexed { idx, floorInfo ->
                            Tab(
                                selected = idx == selectedIndex,
                                onClick = {
                                    selectedIndex = idx
                                    scope.launch {
                                        listState.animateScrollToItem(1)
                                    }
                                },
                                text = {
                                    Text(
                                        text = "${floorInfo.floorNum}층",
                                        textAlign = TextAlign.Center,
                                        style = if (idx == selectedIndex) KUBFAndroidTheme.typography.regular14 else KUBFAndroidTheme.typography.medium14,
                                        color = if (idx == selectedIndex) MainGreen else Gray4
                                    )
                                }
                            )
                        }
                    }
                }
            }
            item {
                Spacer(Modifier.height(16.dp))
                FloorComponent(current) { }
            }
        }
        if (showSearchPopup) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(0.4f))
                        .clickable { showSearchPopup = false })
                Box(
                    modifier = Modifier
                        .size(width = 328.dp, height = 302.dp)
                        .align(Alignment.Center)
                        .offset(y = (-60).dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }) {},
                    contentAlignment = Alignment.Center
                ) {
                    SearchPopup(
                        modifier = Modifier,
                        value = searchValue,
                        onValueChange = { searchValue = it },
                        onClose = {
                            showSearchPopup = false
                        }
                    )
                }
            }


        }
    }
}

@Preview
@Composable
private fun PreviewBuilding() {
    val doors = mutableListOf(Door("https://", "경영관", "A", false))
    doors.add(Door("https://", "경영관", "A-2", true))
    val facilities = Facility.entries.toList()
    val urllist = mutableListOf("httpsL")
    val rooms = mutableListOf(Room(urllist, "101", "전산실습실", "강의실", mutableListOf<String>()))
    val floorInfos = mutableListOf(FloorInfo(1, "https://", facilities, rooms))
    floorInfos.add(FloorInfo(2, "https://", facilities, rooms))
    //floorInfos.add(FloorInfo(3,"https://",features, rooms))
    val totalFloor = TotalFloor(2, floorInfos)
    val building =
        BuildingInfo("경영관", 2, "경영대학", "http://", Notes("2층 구름다리로", mutableListOf("", "")))
    BuildingInfoScreen(
        building, facilities, doors, totalFloor,
        onBack = {},
        onSearch = {},
        onDoorClick = {})

}