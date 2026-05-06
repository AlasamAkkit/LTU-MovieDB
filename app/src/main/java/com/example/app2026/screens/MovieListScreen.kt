package com.example.app2026.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.app2026.components.MovieListItemCard
import com.example.app2026.components.NoConnectionState
import com.example.app2026.models.Movie
import com.example.app2026.models.MovieListType
import com.example.app2026.viewmodel.MovieViewModel

@Composable
fun MovieListScreen(
    navController: NavHostController,
    viewModel: MovieViewModel
) {
    val selectedViewType by viewModel.selectedViewType.collectAsState()
    val movies by viewModel.movies.collectAsState()
    val isConnected by viewModel.isConnected.collectAsState()

    Column {
        Button(
            onClick = { navController.navigate("movie_grid") },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Open Grid Screen")
        }

        MovieTypeSelector(
            selectedType = selectedViewType,
            onTypeSelected = viewModel::selectViewType
        )

        Button(
            onClick = { viewModel.clearCachedList() },
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text("Clear Cached List")
        }

        if (movies.isEmpty() && !isConnected) {
            NoConnectionState(
                onRetryClick = { viewModel.retryCurrentSelection() }
            )
        } else if (movies.isEmpty()) {
            Text(
                text = "Loading movies...",
                modifier = Modifier.padding(16.dp)
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

@Composable
fun MovieTypeSelector(
    selectedType: MovieListType,
    onTypeSelected: (MovieListType) -> Unit
) {
    SingleChoiceSegmentedButtonRow(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        MovieListType.entries.forEachIndexed { index, type ->
            SegmentedButton(
                selected = selectedType == type,
                onClick = { onTypeSelected(type) },
                shape = androidx.compose.material3.SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = MovieListType.entries.size
                )
            ) {
                Text(type.displayName)
            }
        }
    }
}