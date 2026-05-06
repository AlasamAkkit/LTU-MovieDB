package com.example.app2026.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.app2026.models.FavoriteMovieEntity
import com.example.app2026.utils.Constants
import com.example.app2026.viewmodel.FavoritesViewModel

@Composable
fun FavoritesScreen(
    navController: NavHostController,
    favoritesViewModel: FavoritesViewModel
) {
    val favorites by favoritesViewModel.favoritesFlow.collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Back")
        }

        if (favorites.isEmpty()) {
            Text(
                text = "No favorites yet",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(favorites) { favorite ->
                    FavoriteMovieCard(
                        favorite = favorite,
                        onOpen = {
                            navController.navigate("movie_detail/${favorite.id}")
                        },
                        onDelete = {
                            favoritesViewModel.removeFavorite(favorite)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteMovieCard(
    favorite: FavoriteMovieEntity,
    onOpen: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onOpen() }
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = Constants.POSTER_IMAGE_BASE_URL +
                        Constants.POSTER_IMAGE_BASE_WIDTH +
                        favorite.posterPath,
                contentDescription = favorite.title,
                modifier = Modifier
                    .width(92.dp)
                    .height(138.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = favorite.title,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    text = favorite.releaseDate,
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.size(12.dp))

                Button(onClick = onDelete) {
                    Text("Delete")
                }
            }
        }
    }
}