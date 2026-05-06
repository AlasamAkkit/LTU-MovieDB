package com.example.app2026.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.app2026.components.MovieGenres
import com.example.app2026.components.MovieHomepageLink
import com.example.app2026.components.MovieImdbLink
import com.example.app2026.components.NoConnectionState
import com.example.app2026.utils.Constants
import com.example.app2026.viewmodel.MovieViewModel

@Composable
fun MovieDetailScreen(
    navController: NavHostController,
    viewModel: MovieViewModel,
    movieId: Long
) {
    val movie by viewModel.observeMovie(movieId).collectAsState(initial = null)
    val isConnected by viewModel.isConnected.collectAsState()

    if (movie == null && !isConnected) {
        NoConnectionState(
            title = "Movie detail unavailable",
            message = "This movie is not in the currently cached list.",
            onRetryClick = { viewModel.retryCurrentSelection() }
        )
        return
    }

    if (movie == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading movie detail...")
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Row {
                Button(onClick = { navController.popBackStack() }) {
                    Text("Go Back")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = { navController.navigate("movie_media/${movie!!.id}") }) {
                    Text("Reviews & Videos")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            AsyncImage(
                model = Constants.POSTER_IMAGE_BASE_URL +
                        Constants.POSTER_IMAGE_BASE_WIDTH +
                        movie!!.posterPath,
                contentDescription = movie!!.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = movie!!.title, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = movie!!.releaseDate, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            MovieGenres(genres = movie!!.genres)
            Spacer(modifier = Modifier.height(8.dp))
            MovieHomepageLink(homepage = movie!!.homepage)
            Spacer(modifier = Modifier.height(8.dp))
            MovieImdbLink(imdbId = movie!!.imdbId)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = movie!!.overview, style = MaterialTheme.typography.bodyLarge)
        }
    }
}