package com.example.naturediary

import androidx.compose.ui.graphics.Color

fun temperatureColor(temperature: Double): Color {
    return when {
        temperature < 0 -> {
            // The alpha starts higher (lighter color) for temperatures close to 0 and
            // approaches 1.0 (darker color) as temperature decreases to -30.
            val alpha = 1f - ((temperature + 30) / 30).coerceIn(0.0, 1.0).toFloat()
            Color.Blue.copy(alpha = alpha)
        }
        temperature == 0.0 -> Color.LightGray
        else -> {
            // As the temperature increases, the color becomes more opaque.
            val alpha = (temperature / 30).coerceIn(0.0, 1.0).toFloat()
            Color.Red.copy(alpha = alpha)
        }
    }
}
