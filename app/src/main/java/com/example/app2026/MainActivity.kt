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
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.app2026.models.MovieReview
import com.example.app2026.models.MovieVideo
import com.example.app2026.repository.TmdbRepository

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

        composable("movie_grid") {
            MovieGridScreen(navController)
        }

        composable(
            route = "movie_detail/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.LongType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getLong("movieId") ?: 0L
            MovieDetailScreen(navController = navController, movieId = movieId)
        }

        composable(
            route = "movie_media/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.LongType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getLong("movieId") ?: 0L
            MovieMediaScreen(navController = navController, movieId = movieId)
        }
    }
}

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
            Row {
                Button(onClick = { navController.popBackStack() }) {
                    Text("Go Back")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = { navController.navigate("movie_media/${movie.id}") }) {
                    Text("Reviews & Videos")
                }
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieGridScreen(navController: NavHostController) {
    val movies = Movies().getMovies()

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

                            Spacer(modifier = Modifier.height(4.dp))

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
                videoUrl = "https://storage.googleapis.com/exoplayer-test-media-0/play.mp3"
            )

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
                    ReviewCard(review = review)
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
                    VideoCard(video = video)
                }
            }
        }
    }
}

@Composable
fun ReviewCard(review: MovieReview) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .padding(end = 12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = review.author,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = review.content,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun VideoCard(video: MovieVideo) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .width(260.dp)
            .padding(end = 12.dp)
            .clickable {
                if (video.site.equals("YouTube", ignoreCase = true) && video.key.isNotBlank()) {
                    val appIntent = Intent(
                        Intent.ACTION_VIEW,
                        "vnd.youtube:${video.key}".toUri()
                    )
                    val webIntent = Intent(
                        Intent.ACTION_VIEW,
                        "https://www.youtube.com/watch?v=${video.key}".toUri()
                    )

                    try {
                        context.startActivity(appIntent)
                    } catch (_: ActivityNotFoundException) {
                        context.startActivity(webIntent)
                    }
                }
            }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = video.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Site: ${video.site}",
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = "Type: ${video.type}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun EmbeddedVideoPlayer(videoUrl: String) {
    val context = LocalContext.current

    val exoPlayer = remember(videoUrl) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            prepare()
            playWhenReady = false
        }
    }

    AndroidView(
        factory = {
            PlayerView(it).apply {
                player = exoPlayer
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    )

    LaunchedEffect(Unit) {
        // no-op
    }

    androidx.compose.runtime.DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }
}