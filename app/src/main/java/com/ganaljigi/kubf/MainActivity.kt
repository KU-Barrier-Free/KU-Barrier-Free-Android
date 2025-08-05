package com.ganaljigi.kubf

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.ganaljigi.kubf.navigation.MainNavHost
import com.ganaljigi.kubf.ui.helper.screen.HelperScreen
import com.ganaljigi.kubf.ui.helper.screen.SupportScreen
import com.ganaljigi.kubf.ui.roominfo.RoomInfoScreenPreview
import com.ganaljigi.kubf.ui.roominfo.RoomPicPreview
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme

//import com.kakao.sdk.common.util.Utility

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //WindowCompat.setDecorFitsSystemWindows(window, false)
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
//                HelperScreen(
//                    onBackClick = { finish() },
//                    onNoticeClick = { url ->
//                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                        startActivity(intent)
//                    }
//                )
                //SupportScreen{}
                RoomInfoScreenPreview()
            }
        }
    }
}
