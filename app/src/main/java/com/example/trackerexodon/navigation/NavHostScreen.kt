package com.example.trackerexodon.navigation

import ExpenseHistoryScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trackerexodon.screens.AboutScreen
import com.example.trackerexodon.screens.AddExpenseScreen
import com.example.trackerexodon.screens.HomeScreen
import com.example.trackerexodon.screens.SplashScreen

@Composable
fun NavHostScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.SPLASH_SCREEN ) {
        composable(Routes.SPLASH_SCREEN) {
            SplashScreen(navController)
        }
        composable(Routes.HOME_SCREEN) {
            HomeScreen(navController)
        }
        composable(Routes.ADD_EXPENSE) {
            AddExpenseScreen(navController)
        }
        composable(Routes.EXPENSE_HISTORY) {
            ExpenseHistoryScreen(navController)
        }
        composable(Routes.ABOUT_SCREEN) {
            AboutScreen(navController)
        }
    }
}