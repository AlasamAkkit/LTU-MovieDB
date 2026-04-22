package com.example.app2026.components

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