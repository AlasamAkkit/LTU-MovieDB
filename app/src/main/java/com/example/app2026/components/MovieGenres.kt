package com.example.app2026.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MovieGenres(genres: List<String>, modifier: Modifier = Modifier) {
    if (genres.isEmpty()) return

    Text(
        text = genres.joinToString(", "),
        style = MaterialTheme.typography.bodySmall,
        modifier = modifier
    )
}