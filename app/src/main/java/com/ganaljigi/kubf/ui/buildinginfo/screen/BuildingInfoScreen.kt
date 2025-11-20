package com.ganaljigi.kubf.ui.buildinginfo.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.ganalijigi.kubf.R
import com.ganaljigi.kubf.ui.buildinginfo.component.DoorComponent
import com.ganaljigi.kubf.ui.buildinginfo.component.FacilityComponent
import com.ganaljigi.kubf.ui.buildinginfo.component.FloorComponent
import com.ganaljigi.kubf.ui.buildinginfo.component.NoteComponent
import com.ganaljigi.kubf.ui.buildinginfo.component.SearchPopup
import com.ganaljigi.kubf.ui.buildinginfo.model.Room
import com.ganaljigi.kubf.ui.buildinginfo.viewmodel.BuildingViewModel
import com.ganaljigi.kubf.ui.theme.Gray3
import com.ganaljigi.kubf.ui.theme.Gray4
import com.ganaljigi.kubf.ui.theme.MainGreen
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun BuildingInfoScreen(
    buildingId: Long,
    onBack: () -> Unit,
    onRoomClick: ( Room, String)-> Unit,
    viewModel: BuildingViewModel = hiltViewModel(),
    ) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(buildingId) {
        viewModel.init(buildingId)
        viewModel.clearQuery()
    }
    var selectedIndex by remember { mutableIntStateOf(0) }
    val floors = uiState.totalFloor.floorList
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val safeIndex = selectedIndex.coerceIn(0, (floors.size - 1).coerceAtLeast(0))
    val current = floors.getOrNull(safeIndex)

    var showSearchPopup by remember { mutableStateOf(false) }

    LaunchedEffect(floors) {
        if (floors.isNotEmpty()){
            val oneFloorIndex = floors.indexOfFirst { f ->
                f.floorLabel.trim().equals("1")
            }
            selectedIndex = if (oneFloorIndex >= 0) oneFloorIndex else 0
        }
    }

    if (floors.isEmpty()) {
        Scaffold(
            containerColor = Color.White,
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(painter = painterResource(R.drawable.ic_backarrow),
                                contentDescription = "뒤로가기")
                        }
                    },
                    title = {
                        Text(
                            text = uiState.buildingInfo.name,
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
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_search_bar_leading),
                                contentDescription = "검색"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White,
                        titleContentColor = Color.Black
                    )
                )
            }
        ) { inner ->
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(inner), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            if (showSearchPopup) {
                Dialog(
                    onDismissRequest = {
                        showSearchPopup = false
                        viewModel.clearQuery()
                    }
                ) {
                    SearchPopup(
                        onClose = {
                            showSearchPopup = false
                            viewModel.clearQuery()
                        },
                        onRoomClick = {}
                    )
                }
            }
        }
        return
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(painter = painterResource(R.drawable.ic_backarrow),
                            contentDescription = "뒤로가기")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
                title = {
                    Text(
                        text = uiState.buildingInfo.name,
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
                .background(Color.White)
        ) {
            item {
                // 건물 이미지
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    AsyncImage(
                        model = uiState.buildingInfo.imageUrl,
                        contentDescription = "${uiState.buildingInfo.name} 이미지",
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
                        text = uiState.buildingInfo.name,
                        style = KUBFAndroidTheme.typography.bold18
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "건물번호: ${uiState.buildingInfo.number}",
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
                    text = uiState.buildingInfo.department,
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
                    facilities = uiState.buildingInfo.facilities
                )
                Spacer(Modifier.height(20.dp))
                Text(
                    text = "출입문",
                    style = KUBFAndroidTheme.typography.semiBold16,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                DoorComponent(doors = uiState.buildingInfo.doors)
                Spacer(Modifier.height(20.dp))
                if (uiState.buildingInfo.notes.isNotEmpty()) {
                    Text(
                        text = "특이사항",
                        style = KUBFAndroidTheme.typography.semiBold16,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    uiState.buildingInfo.notes.forEachIndexed { idx, note ->
                        NoteComponent(note = note)
                        if (idx < uiState.buildingInfo.notes.lastIndex)
                            Spacer(Modifier.height(8.dp))
                    }
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
                if (uiState.totalFloor.num < 7) {
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
                        containerColor = Color.White
                    ) {
                        floors.forEachIndexed { idx, floorInfo ->
                            Tab(
                                selected = idx == selectedIndex,
                                onClick = {
                                    selectedIndex = idx
                                    scope.launch {
                                        listState.scrollToItem(1)
                                    }
                                },
                                text = {
                                    Text(
                                        text = "${floorInfo.floorLabel}층",
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
                        edgePadding = 0.dp,
                        containerColor = Color.White
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
                                        text = "${floorInfo.floorLabel}층",
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
                current?.let { floor ->
                    FloorComponent(
                        current = floor,
                        onRoomClick = { room ->
                            onRoomClick(room, uiState.buildingInfo.name)
                        }
                    )
                }
            }
        }
        if (showSearchPopup) {
            Dialog(
                onDismissRequest = {
                    showSearchPopup = false
                    viewModel.clearQuery()
                }
            ) {
                SearchPopup(
                    onClose = {
                        showSearchPopup = false
                        viewModel.clearQuery()
                    },
                    onRoomClick = { result ->
                        result.room?.let { room ->
                            onRoomClick(room, uiState.buildingInfo.name)
                        }
                    }
                )
            }
        }
    }
}
@Preview
@Composable
private fun PreviewBuilding() {

}
