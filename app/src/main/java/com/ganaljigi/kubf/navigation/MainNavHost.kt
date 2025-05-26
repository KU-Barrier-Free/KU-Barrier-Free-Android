package com.ganaljigi.kubf.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MainNavHost(
    padding: PaddingValues,
    navController: NavHostController,
) {

    NavHost(
        navController = navController,
        startDestination = Routes.Splash,
    ) {
        composable<Routes.Splash> {
//            SplashScreen(
//                padding = padding,
//                navigateToHome = {
//                    navController.navigate(Routes.Home) {
//                        popUpTo(Routes.Splash) { inclusive = true }
//                    }
//                },
//            )
        }

        composable<Routes.Home> {
//            HomeScreen(
//                padding = padding,
//                navigateToHelper = { navController.navigate(Routes.Helper) },
//                navigateToBuildingInfo = { navController.navigate(Routes.BuildingInfo) },
//            )
        }

        composable<Routes.Helper> {
//            HelperScreen(
//                padding = padding,
//                navigateToNotice = { navController.navigate(Routes.Notice) },
//                navigateBack = { navController.popBackStack() },
//            )
        }

        composable<Routes.BuildingInfo> {
//             BuildingInfoScreen(
//                padding = padding,
//                navigateToRoomInfo = { roomId ->
//                    navController.navigate(Routes.RoomInfo(roomId))
//                },
//                navigateBack = { navController.popBackStack() },
//             )
        }

        composable<Routes.RoomInfo> {
//             RoomInfoScreen(
//                padding = padding,
//                roomId = it.toRoute<Routes.RoomInfo>().roomId,
//                navigateBack = { navController.popBackStack() },
//             )
        }
    }
}