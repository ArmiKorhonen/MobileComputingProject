/**
 * File: Utils.kt
 *
 * Description: Contains utility functions and composable functions that provide reusable UI components and logic
 * for the Nature Diary app. It includes a function for determining the color representation of temperatures, a
 * composable function for displaying diary entries as cards in the UI, and a composable function for the app's
 * leaf logo. The temperatureColor function dynamically adjusts the color based on the temperature value, enhancing
 * data visualization. The EntryItem composable arranges diary entry data visually, and LeafLogo displays the app's
 * logo with customizable positioning, supporting branding and design consistency across the app.
 */

package com.example.naturediary

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource

fun temperatureColor(temperature: Double): Color {
    return when {
        temperature < 0 -> {
            // The alpha starts higher (lighter color) for temperatures close to 0 and
            // approaches 1.0 (darker color) as temperature decreases to -30.
            val alpha = 1f - ((temperature + 30) / 30).coerceIn(0.0, 1.0).toFloat()
            Color(0xFF1976D2).copy(alpha = alpha)
        }
        temperature == 0.0 -> Color.LightGray
        else -> {
            // As the temperature increases, the color becomes more opaque.
            val alpha = (temperature / 30).coerceIn(0.0, 1.0).toFloat()
            Color(0xFFD32F2F).copy(alpha = alpha)
        }
    }
}

@Composable
fun EntryItem(entry: DiaryEntry, onClick: (() -> Unit)? = null) {
    val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer) // Sets the card's background color


    ) {
        Row(modifier = Modifier.padding(12.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            // Temperature circle on the left
            Box(modifier = Modifier.size(60.dp)) {
                Canvas(modifier = Modifier.matchParentSize()) {
                    drawCircle(color = temperatureColor(entry.temperature))
                }
                // Text is absolutely positioned to be centered within the Box
                Text(
                    text = "${entry.temperature}°C",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Date, time, and address on the right
            Column {
                Text(text = sdf.format(Date(entry.timestamp)), style = MaterialTheme.typography.titleLarge)
                Text(text = entry.address, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
fun LeafLogo(modifier: Modifier = Modifier) {
    // Use BoxWithConstraints to get access to constraints like maxWidth
    BoxWithConstraints(modifier = modifier) {
        val screenWidth = maxWidth
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp

        // Calculate offsets or positions as needed
        val topOffset = screenHeight * 0.2f // Adjust this value to control vertical positioning
        val horizontalCenterOffset = (screenWidth - 250.dp) / 2 // Centering the logo horizontally, adjust if needed

        Icon(
            painter = painterResource(id = R.drawable.naturediarylogo),
            contentDescription = "Leaf",
            // Apply calculated offsets in modifier
            modifier = Modifier
                .offset(x = horizontalCenterOffset, y = topOffset)
                .size(250.dp) // Use the final size of the leaf after animation
        )

    }
}

