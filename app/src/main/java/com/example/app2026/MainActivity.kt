package com.example.app2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.app2026.database.Movies
import com.example.app2026.models.Movie
import com.example.app2026.ui.theme.App2026Theme
import com.example.app2026.utils.Constants

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App2026Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MovieDBApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MovieDBApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "movie_list",
        modifier = modifier
    ) {
        composable("movie_list") {
            MovieListScreen(navController)
        }

        composable("third_screen") {
            ThirdScreen(navController)
        }
    }
}

@Composable
fun MovieListScreen(navController: NavHostController) {
    Column {
        Button(
            onClick = { navController.navigate("third_screen") },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Go to Third Screen")
        }

        MovieList(movieList = Movies().getMovies())
    }
}

@Composable
fun MovieList(movieList: List<Movie>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(movieList) { movie ->
            MovieListItemCard(
                movie = movie,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun MovieListItemCard(movie: Movie, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Row {
            AsyncImage(
                model = Constants.POSTER_IMAGE_BASE_URL +
                        Constants.POSTER_IMAGE_BASE_WIDTH +
                        movie.posterPath,
                contentDescription = movie.title,
                modifier = Modifier
                    .width(92.dp)
                    .height(138.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    text = movie.releaseDate,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }
}

@Composable
fun ThirdScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Third Screen",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { navController.popBackStack() }) {
                Text("Go Back")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    App2026Theme {
        MovieDBApp()
    }
}