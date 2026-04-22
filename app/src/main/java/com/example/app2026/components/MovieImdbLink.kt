package com.example.app2026.components

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.core.net.toUri

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