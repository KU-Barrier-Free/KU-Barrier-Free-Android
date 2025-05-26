package com.ganaljigi.kubf.ui.buildinginfo.screen

import androidx.compose.foundation.Image
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
import coil3.compose.rememberAsyncImagePainter
import com.ganaljigi.kubf.ui.buildinginfo.component.Door
import com.ganaljigi.kubf.ui.buildinginfo.component.DoorComponent
import com.ganaljigi.kubf.ui.buildinginfo.component.Feature
import com.ganaljigi.kubf.ui.buildinginfo.component.FeatureComponent

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
                .padding(16.dp)
        ) {
            // 건물 이미지
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(building.imageUrl),
                    contentDescription = "${building.name} 이미지",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )
            }
            Spacer(Modifier.height(20.dp))

            // 건물 이름, 번호
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = building.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "건물번호: ${building.number}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF999999)
                )
            }
            Spacer(Modifier.height(24.dp))

            // 소속 부서
            Text(
                text = "소속 부서",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = building.department,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF656565)
            )
            Spacer(Modifier.height(20.dp))

            FeatureComponent(
                features = features,
                onClick = onFeatureClick
            )
            Spacer(Modifier.height(20.dp))
            DoorComponent(doors = doors)

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