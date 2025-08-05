package com.ganaljigi.kubf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ganaljigi.kubf.ui.buildinginfo.model.BuildingInfo
import com.ganaljigi.kubf.ui.buildinginfo.model.Door
import com.ganaljigi.kubf.ui.buildinginfo.model.Facility
import com.ganaljigi.kubf.ui.buildinginfo.model.FloorInfo
import com.ganaljigi.kubf.ui.buildinginfo.model.Notes
import com.ganaljigi.kubf.ui.buildinginfo.model.Room
import com.ganaljigi.kubf.ui.buildinginfo.model.TotalFloor
import com.ganaljigi.kubf.ui.buildinginfo.screen.BuildingInfoScreen
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KUBFAndroidTheme {
//                val navController = rememberNavController()
//
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    MainNavHost(
//                        padding = innerPadding,
//                        navController = navController,
//                    )
//                }
                val doors = mutableListOf(Door("https://", "창의관", "A", false))
                doors.add(Door("https://", "창의관", "B", true))
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
        }
    }
}
