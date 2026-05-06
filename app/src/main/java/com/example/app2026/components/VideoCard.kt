package com.example.app2026.components

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.app2026.models.MovieVideo

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