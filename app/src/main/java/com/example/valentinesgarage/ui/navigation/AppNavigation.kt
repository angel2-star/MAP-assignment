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

// Define all screen routes as constants
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

    }
}