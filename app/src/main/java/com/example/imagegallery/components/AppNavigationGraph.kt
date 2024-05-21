package com.example.imagegallery.components

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.imagegallery.data.response.Item
import com.example.imagegallery.constants.Routes
import com.example.imagegallery.ui.details.DetailsScreen
import com.example.imagegallery.ui.gallery.GalleryScreen

/**
 * @author Shahriar
 * @since ১৭/৫/২৪ ৯:৫৯ AM
 */
@Composable
fun AppNavigationGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME_ROUTE) {
        composable(Routes.HOME_ROUTE) {
            GalleryScreen(navController)
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