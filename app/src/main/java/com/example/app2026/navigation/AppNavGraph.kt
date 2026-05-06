package com.example.app2026.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.app2026.screens.FavoritesScreen
import com.example.app2026.screens.MovieDetailScreen
import com.example.app2026.screens.MovieGridScreen
import com.example.app2026.screens.MovieListScreen
import com.example.app2026.screens.MovieMediaScreen
import com.example.app2026.viewmodel.FavoritesViewModel
import com.example.app2026.viewmodel.MovieViewModel

@Composable
fun AppNavGraph(
    movieViewModel: MovieViewModel,
    favoritesViewModel: FavoritesViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "movie_list",
        modifier = modifier
    ) {
        composable("movie_list") {
            MovieListScreen(
                navController = navController,
                movieViewModel = movieViewModel
            )
        }

        composable("movie_grid") {
            MovieGridScreen(
                navController = navController,
                movieViewModel = movieViewModel
            )
        }

        composable("favorites") {
            FavoritesScreen(
                navController = navController,
                favoritesViewModel = favoritesViewModel
            )
        }

        composable(
            route = "movie_detail/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.LongType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getLong("movieId") ?: 0L
            MovieDetailScreen(
                navController = navController,
                movieId = movieId,
                movieViewModel = movieViewModel,
                favoritesViewModel = favoritesViewModel
            )
        }

        composable(
            route = "movie_media/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.LongType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getLong("movieId") ?: 0L
            MovieMediaScreen(
                navController = navController,
                movieId = movieId,
                viewModel = movieViewModel
            )
        }
    }
}