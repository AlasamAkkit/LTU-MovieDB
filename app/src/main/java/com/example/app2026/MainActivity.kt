package com.example.app2026

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.example.app2026.database.Movies
import com.example.app2026.models.Movie
import com.example.app2026.ui.theme.App2026Theme
import com.example.app2026.utils.Constants
import androidx.core.net.toUri

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

        composable(
            route = "movie_detail/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.LongType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getLong("movieId") ?: 0L
            MovieDetailScreen(navController = navController, movieId = movieId)
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

@Composable
fun MovieGenres(genres: List<String>, modifier: Modifier = Modifier) {
    if (genres.isEmpty()) return

    Text(
        text = genres.joinToString(", "),
        style = MaterialTheme.typography.bodySmall,
        modifier = modifier
    )
}

@Composable
fun MovieHomepageLink(homepage: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    if (homepage.isBlank()) return

    Text(
        text = "Homepage",
        color = Color.Blue,
        textDecoration = TextDecoration.Underline,
        style = MaterialTheme.typography.bodySmall,
        modifier = modifier.clickable {
            val intent = Intent(Intent.ACTION_VIEW, homepage.toUri())
            context.startActivity(intent)
        }
    )
}

@Composable
fun MovieImdbLink(imdbId: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    if (imdbId.isBlank()) return

    val imdbUrl = "https://www.imdb.com/title/$imdbId/"

    Text(
        text = "Open in IMDb",
        color = Color.Blue,
        textDecoration = TextDecoration.Underline,
        style = MaterialTheme.typography.bodySmall,
        modifier = modifier.clickable {
            val imdbAppIntent = Intent(Intent.ACTION_VIEW, imdbUrl.toUri()).apply {
                setPackage("com.imdb.mobile")
            }

            val webIntent = Intent(Intent.ACTION_VIEW, imdbUrl.toUri())

            try {
                context.startActivity(imdbAppIntent)
            } catch (_: ActivityNotFoundException) {
                context.startActivity(webIntent)
            }
        }
    )
}

@Composable
fun MovieListItemCard(
    movie: Movie,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable { onClick() }
    ) {
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

                MovieGenres(genres = movie.genres)
                Spacer(modifier = Modifier.size(8.dp))

                MovieHomepageLink(homepage = movie.homepage)
                Spacer(modifier = Modifier.size(8.dp))

                MovieImdbLink(imdbId = movie.imdbId)
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
fun MovieDetailScreen(navController: NavHostController, movieId: Long) {
    val movie = Movies().getMovies().find { it.id == movieId }

    if (movie == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Movie not found")
        }
        return
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

            AsyncImage(
                model = Constants.POSTER_IMAGE_BASE_URL +
                        Constants.POSTER_IMAGE_BASE_WIDTH +
                        movie.posterPath,
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = movie.title,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = movie.releaseDate,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            MovieGenres(genres = movie.genres)

            Spacer(modifier = Modifier.height(8.dp))

            MovieHomepageLink(homepage = movie.homepage)

            Spacer(modifier = Modifier.height(8.dp))

            MovieImdbLink(imdbId = movie.imdbId)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = movie.overview,
                style = MaterialTheme.typography.bodyLarge
            )
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