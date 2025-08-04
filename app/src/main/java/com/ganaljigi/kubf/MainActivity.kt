package com.ganaljigi.kubf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ganaljigi.kubf.ui.helper.screen.HelperScreen
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme

//import com.kakao.sdk.common.util.Utility

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
                HelperScreen {  }
            }
        }
    }
}
