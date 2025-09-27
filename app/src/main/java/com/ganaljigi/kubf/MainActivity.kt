package com.ganaljigi.kubf

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ganaljigi.kubf.navigation.MainNavHost
import com.ganaljigi.kubf.navigation.Routes
import com.ganaljigi.kubf.ui.theme.KUBFAndroidTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KUBFAndroidTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val context = LocalContext.current

                BackHandler(enabled = currentRoute == Routes.Home::class.qualifiedName) {
                    if (System.currentTimeMillis() - backPressedTime <= 2000) {
                        finish()
                    } else {
                        backPressedTime = System.currentTimeMillis()
                        Toast.makeText(context, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding()
                ) { innerPadding ->
                    MainNavHost(
                        padding = innerPadding,
                        navController = navController,
                    )
                }
            }
        }
    }
}
