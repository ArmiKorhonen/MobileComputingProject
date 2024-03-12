/**
 * File: Theme.kt
 *
 * Description: This file establishes the theme for the NatureDiary app by utilizing the MaterialTheme
 * composable function from Jetpack Compose. It dynamically selects either a light or dark color scheme based
 * on the system settings or an explicitly set theme preference. The color schemes are defined in the
 * Color.kt file and include customizations for various UI elements to align with the app's design requirements.
 */

package com.example.naturediary

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    secondaryContainer = md_theme_light_secondaryContainer,
    background = md_theme_light_background,
    secondary = md_theme_light_secondary,
)

private val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    secondaryContainer = md_theme_dark_secondaryContainer,
    background = md_theme_dark_background,
    secondary = md_theme_dark_secondary,
)


@Composable
fun NatureDiaryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme =
        if (!darkTheme) {
            LightColorScheme
        } else {
            DarkColorScheme
        }
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}