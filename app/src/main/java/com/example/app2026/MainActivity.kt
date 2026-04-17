package com.example.app2026

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.app2026.database.Movies
import com.example.app2026.models.Movie
import com.example.app2026.ui.theme.App2026Theme
import com.example.app2026.utils.Constants

// Main activity = entry point of the app
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Allows UI to draw closer to screen edges
        enableEdgeToEdge()

        setContent {
            App2026Theme {
                // Scaffold provides a standard screen layout structure
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MovieDBApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MovieDBApp(modifier: Modifier = Modifier) {
    // Navigation controller used to move between screens
    val navController = rememberNavController()

    // NavHost defines all available routes/screens
    NavHost(
        navController = navController,
        startDestination = "movie_list",
        modifier = modifier
    ) {
        // Route for the main movie list screen
        composable("movie_list") {
            MovieListScreen(navController)
        }

        // Route for the third screen
        composable("third_screen") {
            ThirdScreen(navController)
        }
    }
}

@Composable
fun MovieListScreen(navController: NavHostController) {
    Column {
        // Button that navigates to the third screen
        Button(
            onClick = { navController.navigate("third_screen") },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Go to Third Screen")
        }

        // Show the movie list using data from Movies.kt
        MovieList(movieList = Movies().getMovies())
    }
}

@Composable
fun MovieList(movieList: List<Movie>, modifier: Modifier = Modifier) {
    // LazyColumn = vertically scrollable list
    LazyColumn(modifier = modifier) {
        // Create one card for each movie
        items(movieList) { movie ->
            MovieListItemCard(
                movie = movie,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun MovieGenres(genres: List<String>, modifier: Modifier = Modifier) {
    // If no genres exist, do not show anything
    if (genres.isEmpty()) return

    // Join the genre list into one string like "Drama, Action"
    Text(
        text = genres.joinToString(", "),
        style = MaterialTheme.typography.bodySmall,
        modifier = modifier
    )
}

@Composable
fun MovieHomepageLink(homepage: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    // If homepage is blank, do not show anything
    if (homepage.isBlank()) return

    Text(
        text = "Homepage",
        color = Color.Blue,
        textDecoration = TextDecoration.Underline,
        style = MaterialTheme.typography.bodySmall,
        modifier = modifier.clickable {
            // Create intent to open the homepage URL in browser
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(homepage))
            context.startActivity(intent)
        }
    )
}

@Composable
fun MovieImdbLink(imdbId: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    // If imdbId is blank, do not show anything
    if (imdbId.isBlank()) return

    // Build the IMDb URL using the imdbId
    val imdbUrl = "https://www.imdb.com/title/$imdbId/"

    Text(
        text = "Open in IMDb",
        color = Color.Blue,
        textDecoration = TextDecoration.Underline,
        style = MaterialTheme.typography.bodySmall,
        modifier = modifier.clickable {
            // Intent targeting IMDb app specifically
            val imdbAppIntent = Intent(Intent.ACTION_VIEW, Uri.parse(imdbUrl)).apply {
                setPackage("com.imdb.mobile")
            }

            // Fallback browser intent
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(imdbUrl))

            // Try IMDb app first, browser if app fails
            try {
                context.startActivity(imdbAppIntent)
            } catch (e: ActivityNotFoundException) {
                context.startActivity(webIntent)
            }
        }
    )
}

@Composable
fun MovieListItemCard(movie: Movie, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Row {
            // Loads movie poster from IMDB URL
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
                // Movie title
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.size(8.dp))

                // Release date
                Text(
                    text = movie.releaseDate,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.size(8.dp))

                // Genres
                MovieGenres(genres = movie.genres)
                Spacer(modifier = Modifier.size(8.dp))

                // Homepage link
                MovieHomepageLink(homepage = movie.homepage)
                Spacer(modifier = Modifier.size(8.dp))

                // IMDb link
                MovieImdbLink(imdbId = movie.imdbId)
                Spacer(modifier = Modifier.size(8.dp))

                // Movie overview, limited to 3 lines
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
            // Third screen title
            Text(
                text = "Third Screen",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Go back to previous screen
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