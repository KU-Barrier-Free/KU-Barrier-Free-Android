package com.ganaljigi.kubf.ui.buildinginfo.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.ganaljigi.kubf.ui.buildinginfo.component.Door
import com.ganaljigi.kubf.ui.buildinginfo.component.DoorComponent
import com.ganaljigi.kubf.ui.buildinginfo.component.Feature
import com.ganaljigi.kubf.ui.buildinginfo.component.FeatureComponent
import com.ganaljigi.kubf.ui.buildinginfo.component.Room
import com.ganaljigi.kubf.ui.buildinginfo.component.RoomComponent
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme

data class BuildingInfo(
    val name: String,
    val number: Int,
    val department: String,
    val imageUrl: String,
    val floors: String?
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuildingInfoScreen(
    building: BuildingInfo,
    features: List<Feature>,
    doors: List<Door>,
    onBack: () -> Unit,
    onSearch: () -> Unit,
    onFeatureClick: (Feature) -> Unit,
    onDoorClick: (Door) -> Unit
) {
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
                        textAlign = TextAlign.Center
                    )
                },
                actions = {
                    IconButton(onClick = onSearch) {
                        Icon(Icons.Filled.Search, contentDescription = "검색")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                //.padding(16.dp)
        ) {
            // 건물 이미지
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                AsyncImage(
                    model = building.imageUrl,
                    contentDescription = "${building.name} 이미지",
                    modifier = Modifier.background(color = Color.LightGray).fillMaxWidth()
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
                    color = Color(0xFF999999)
                )
            }
            Spacer(Modifier.height(24.dp))

            // 소속 부서
            Text(
                text = "소속 부서",
                style = KUBFAndroidTheme.typography.semiBold16,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = building.department,
                style = KUBFAndroidTheme.typography.regular14,
                color = Color(0xFF656565),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(20.dp))
            Text(
                text = "주요시설",
                style = KUBFAndroidTheme.typography.semiBold16,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            FeatureComponent(
                features = features,
                onClick = onFeatureClick
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
            RoomComponent(Room("http://","201","전산실습실","강의실","경사로"))
        }
    }
}

@Preview
@Composable
private fun PreviewBuilding() {
    val doors = mutableListOf(Door("https://", "경영관", "A", false))
    doors.add(Door("https://", "경영관", "A-2", true))
    val features = mutableListOf(Feature("카페"))
    features.add(Feature("편의점"))
    features.add(Feature("복사실"))
    features.add(Feature("편의점"))
    features.add(Feature("복사실"))
    features.add(Feature("편의점"))
    features.add(Feature("복사실"))
    features.add(Feature("편의점"))
    features.add(Feature("복사실"))

    val building = BuildingInfo("경영관", 2, "경영대학", "http://", "5")
    BuildingInfoScreen(
        building, features, doors,
        onBack = {},
        onSearch = {},
        onFeatureClick = {},
        onDoorClick = {})

}