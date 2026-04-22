package com.example.app2026.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.app2026.models.Movie
import com.example.app2026.utils.Constants

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