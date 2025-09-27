package com.ganaljigi.kubf.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ganaljigi.kubf.ui.buildinginfo.screen.BuildingInfoScreen
import com.ganaljigi.kubf.ui.buildinginfo.viewmodel.BuildingViewModel
import com.ganaljigi.kubf.ui.helper.screen.HelperScreen
import com.ganaljigi.kubf.ui.home.screen.HomeScreen
import com.ganaljigi.kubf.ui.home.screen.HomeSearchScreen
import com.ganaljigi.kubf.ui.home.viewmodel.HomeViewModel
import com.ganaljigi.kubf.ui.roominfo.RoomInfoScreen
import com.ganaljigi.kubf.ui.roominfo.viewmodel.RoomInfoViewModel

@Composable
fun MainNavHost(
    padding: PaddingValues,
    navController: NavHostController,
) {

    val homeViewModel = hiltViewModel<HomeViewModel>()

    NavHost(
        navController = navController,
        startDestination = Routes.Home,
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

        composable<Routes.Home> { navBackStackEntry ->
            HomeScreen(
                padding = padding,
                navigateToHelper = { navController.navigate(Routes.Helper) },
                navigateToBuildingInfo = { navController.navigate(Routes.BuildingInfo(it.toLong())) },
                navigateToSearch = { title ->
                    navController.navigate(Routes.HomeSearch(title))
                },
                viewModel = homeViewModel,
            )
        }

        composable<Routes.HomeSearch> { navBackStackEntry ->
            val searchMode = navBackStackEntry.toRoute<Routes.HomeSearch>().title

            HomeSearchScreen(
                padding = padding,
                searchMode = searchMode,
                navigateUp = { navController.popBackStack() },
                viewModel = homeViewModel,
            )
        }

        composable<Routes.Helper> {
            HelperScreen(
//                padding = padding,
//                navigateToNotice = { navController.navigate(Routes.Notice) },
                onBackClick = { navController.popBackStack() },
            )
        }

        composable<Routes.Notice> {
//            NoticeScreen(
//                padding = padding,
//                navigateBack = { navController.popBackStack() },
//            )
        }

        composable<Routes.BuildingInfo> { navBackStackEntry ->
            val buildingId = navBackStackEntry.toRoute<Routes.BuildingInfo>().number

             BuildingInfoScreen(
                buildingId = buildingId,
                onRoomClick = { room, buildingName ->
                    navController.navigate(Routes.RoomInfo(
                        buildingId = buildingId,
                        spaceId = room.id,
                        type = if (room.isLecture) 1 else 0,
                        buildingName = buildingName
                    ))

                },
                onBack = { navController.popBackStack() }
             )
        }

        composable<Routes.RoomInfo> { navBackStackEntry ->
            val roomNumber = navBackStackEntry.toRoute<Routes.RoomInfo>()
             RoomInfoScreen(
                onBackClick = {navController.popBackStack()},
             )
        }
    }
}