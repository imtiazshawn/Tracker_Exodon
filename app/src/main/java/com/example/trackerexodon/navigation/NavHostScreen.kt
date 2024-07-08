package com.example.trackerexodon.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trackerexodon.screens.AddExpenseScreen
import com.example.trackerexodon.screens.HomeScreen

@Composable
fun NavHostScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME_SCREEN ) {
        composable(Routes.HOME_SCREEN) {
            HomeScreen(navController)
        }
        composable(Routes.ADD_EXPENSE) {
            AddExpenseScreen(navController)
        }
    }
}