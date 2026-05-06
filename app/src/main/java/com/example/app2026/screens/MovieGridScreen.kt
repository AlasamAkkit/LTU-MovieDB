package com.example.app2026.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.app2026.utils.Constants
import com.example.app2026.viewmodel.MovieViewModel

@Composable
fun MovieGridScreen(
    navController: NavHostController,
    movieViewModel: MovieViewModel
) {
    val movies by movieViewModel.movies.collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Back")
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            items(movies) { movie ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            navController.navigate("movie_detail/${movie.id}")
                        }
                ) {
                    Column {
                        AsyncImage(
                            model = Constants.POSTER_IMAGE_BASE_URL +
                                    Constants.POSTER_IMAGE_BASE_WIDTH +
                                    movie.posterPath,
                            contentDescription = movie.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp),
                            contentScale = ContentScale.Crop
                        )

                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(
                                text = movie.title,
                                style = MaterialTheme.typography.titleMedium,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )

                            Text(
                                text = movie.releaseDate,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}