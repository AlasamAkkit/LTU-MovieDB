package com.example.app2026.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.app2026.components.MovieListItemCard
import com.example.app2026.models.Movie
import com.example.app2026.models.MovieListType
import com.example.app2026.viewmodel.MovieViewModel

@Composable
fun MovieListScreen(
    navController: NavHostController,
    movieViewModel: MovieViewModel
) {
    val movies by movieViewModel.movies.collectAsState()
    val selectedViewType by movieViewModel.selectedViewType.collectAsState()
    val isConnected by movieViewModel.isConnected.collectAsState()

    Column {
        Row(modifier = Modifier.padding(8.dp)) {
            Button(
                onClick = { movieViewModel.selectViewType(MovieListType.POPULAR) },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("Popular")
            }

            Button(
                onClick = { movieViewModel.selectViewType(MovieListType.TOP_RATED) },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("Top Rated")
            }
        }

        Row(modifier = Modifier.padding(horizontal = 8.dp)) {
            Button(
                onClick = { navController.navigate("movie_grid") },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("Open Grid Screen")
            }

            Button(
                onClick = { navController.navigate("favorites") }
            ) {
                Text("Favorites")
            }
        }

        Text(
            text = "Current category: ${selectedViewType.displayName}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(8.dp)
        )

        if (!isConnected && movies.isEmpty()) {
            Text(
                text = "No internet connection and no cached movies for this category.",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            MovieList(
                movieList = movies,
                onMovieClick = { movie ->
                    navController.navigate("movie_detail/${movie.id}")
                }
            )
        }
    }
}

@Composable
fun MovieList(
    movieList: List<Movie>,
    onMovieClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(movieList) { movie ->
            MovieListItemCard(
                movie = movie,
                onClick = { onMovieClick(movie) },
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}