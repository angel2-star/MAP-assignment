package com.example.valentinesgarage.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.valentinesgarage.ui.screens.CheckInScreen
import com.example.valentinesgarage.ui.screens.ChecklistScreen
import com.example.valentinesgarage.ui.screens.LoginScreen
import com.example.valentinesgarage.ui.screens.ReportsScreen

//Define all screen routes as constants
object Routes {
    const val LOGIN = "login"
    const val CHECKIN = "checkin"
    const val CHECKLIST = "checklist/{vehicleId}"
    const val REPORTS = "reports"

    fun checklist(vehicleId: Int) = "checklist/$vehicleId"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navcontroller = navController,
        startDestination = Routes.LOGIN
    ){
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = { role ->
                    if (role == "manager") {
                        navController.navigate(Routes.REPORTS) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Routes.CHECKIN) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    }
                }
            )
        }
        composable(Routes.CHECKIN) {
            CheckInScreen(
                onGoToChecklist = { vehicleId ->
                    navController.navigate(Routes.checklist(vehicleId))
                },
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(
            route = Routes.CHECKLIST,
            arguments = listOf(navArgument("vehicleId") { type = NavType.IntType })
        ) { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getInt("vehicleId") ?: -1
            ChecklistScreen(
                vehicleId = vehicleId,
                onBack = { navController.popBackStack() },
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.REPORTS) {
            ReportsScreen(
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}
