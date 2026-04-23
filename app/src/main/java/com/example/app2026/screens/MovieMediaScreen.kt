package com.example.app2026.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.app2026.components.EmbeddedVideoPlayer
import com.example.app2026.components.ReviewCard
import com.example.app2026.components.VideoCard
import com.example.app2026.models.MovieReview
import com.example.app2026.models.MovieVideo
import com.example.app2026.repository.TmdbRepository

@Composable
fun MovieMediaScreen(navController: NavHostController, movieId: Long) {
    val repository = remember { TmdbRepository() }
    var reviews by remember { mutableStateOf<List<MovieReview>>(emptyList()) }
    var videos by remember { mutableStateOf<List<MovieVideo>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(movieId) {
        isLoading = true
        errorMessage = null
        try {
            reviews = repository.getReviews(movieId)
            videos = repository.getVideos(movieId)
        } catch (e: Exception) {
            errorMessage = e.message ?: "Failed to load data"
        } finally {
            isLoading = false
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Button(onClick = { navController.popBackStack() }) {
                Text("Go Back")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Embedded Video",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            EmbeddedVideoPlayer(
                videoUrl = "https://test-videos.co.uk/vids/bigbuckbunny/mp4/av1/360/Big_Buck_Bunny_360_10s_1MB.mp4"
            )

            Spacer(modifier = Modifier.height(24.dp))

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Reviews",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        if (isLoading) {
            item { Text("Loading...") }
        }

        errorMessage?.let { message ->
            item { Text("Error: $message") }
        }

        item {
            LazyRow {
                items(reviews) { review ->
                    ReviewCard(review)
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Videos",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            LazyRow {
                items(videos) { video ->
                    VideoCard(video)
                }
            }
        }
    }
}