package com.example.app2026.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.app2026.R

@Composable
fun NoConnectionState(
    title: String = "No internet connection",
    message: String = "This list is not cached yet. Turn the internet back on and it will load automatically.",
    onRetryClick: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_no_connection),
            contentDescription = "No connection"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium
        )

        if (onRetryClick != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRetryClick) {
                Text("Retry sync")
            }
        }
    }
}