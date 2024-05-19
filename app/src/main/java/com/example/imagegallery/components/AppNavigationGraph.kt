package com.example.imagegallery.components

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.imagegallery.data.response.Item
import com.example.imagegallery.routes.Routes
import com.example.imagegallery.screens.DetailsScreen
import com.example.imagegallery.screens.HomeScreen

/**
 * @author Shahriar
 * @since ১৭/৫/২৪ ৯:৫৯ AM
 */
@Composable
fun AppNavigationGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME_ROUTE) {
        composable(Routes.HOME_ROUTE) {
            HomeScreen(navController)
        }
        composable(Routes.DETAILS_ROUTE) {
            val result = navController.previousBackStackEntry?.savedStateHandle?.get<Item>("item")
            result?.let { item ->
                DetailsScreen(
                    item = item,
                    onBackPress = { navController.navigateUp() }
                )
            }
        }
    }
}