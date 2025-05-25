package com.ganaljigi.kubf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ganaljigi.kubf.ui.buildinginfo.component.Door
import com.ganaljigi.kubf.ui.buildinginfo.component.Feature
import com.ganaljigi.kubf.ui.buildinginfo.screen.BuildingInfo
import com.ganaljigi.kubf.ui.buildinginfo.screen.BuildingInfoScreen
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent(){
            val doors = mutableListOf(Door("https://", "창의관", "A", false))
            doors.add(Door("https://","창의관","A-2",true))
            doors.add(Door("https://","창의관","A-2",false))
            doors.add(Door("https://","창의관","A-2",true))
            doors.add(Door("https://","창의관","A-2",true))
            val features = mutableListOf(Feature("카페"))
            features.add(Feature("편의점"))
            features.add(Feature("복사실"))
            features.add(Feature("편의점"))
            features.add(Feature("복사실"))
            features.add(Feature("편의점"))
            features.add(Feature("복사실"))
            features.add(Feature("편의점"))
            features.add(Feature("복사실"))
            val building = BuildingInfo("창의관",1,"강의동","http://","5")
            BuildingInfoScreen(building, features, doors,
                onBack = {},
                onSearch = {},
                onFeatureClick = {},
                onDoorClick = {})
        }


    }
}



