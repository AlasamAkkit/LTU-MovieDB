package com.example.app2026.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.app2026.screens.MovieDetailScreen
import com.example.app2026.screens.MovieGridScreen
import com.example.app2026.screens.MovieListScreen
import com.example.app2026.screens.MovieMediaScreen
import com.example.app2026.viewmodel.MovieViewModel

@Composable
fun AppNavGraph(
    viewModel: MovieViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "movie_list",
        modifier = modifier
    ) {
        composable("movie_list") {
            MovieListScreen(navController, viewModel)
        }

        composable("movie_grid") {
            MovieGridScreen(navController, viewModel)
        }

        composable(
            route = "movie_detail/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.LongType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getLong("movieId") ?: 0L
            MovieDetailScreen(navController, viewModel, movieId)
        }

        composable(
            route = "movie_media/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.LongType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getLong("movieId") ?: 0L
            MovieMediaScreen(navController, viewModel, movieId)
        }
    }
}
