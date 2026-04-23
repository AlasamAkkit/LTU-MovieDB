package com.example.app2026.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.app2026.components.MovieListItemCard
import com.example.app2026.database.Movies
import com.example.app2026.models.Movie

@Composable
fun MovieListScreen(navController: NavHostController) {
    Column {
        Button(
            onClick = { navController.navigate("movie_grid") },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Open Grid Screen")
        }

        MovieList(
            movieList = Movies().getMovies(),
            onMovieClick = { movie ->
                navController.navigate("movie_detail/${movie.id}")
            }
        )
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