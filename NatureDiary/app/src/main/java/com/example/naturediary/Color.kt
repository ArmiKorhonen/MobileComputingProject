/**
 * File: Color.kt
 *
 * Description: Defines the color palette for the application's light and dark themes. It includes colors for
 * primary, onPrimary, primaryContainer, secondaryContainer, background, and secondary variations. These
 * colors follow Material Design guidelines and are used throughout the app to ensure consistency and visual
 * appeal in both light and dark modes.
 */

package com.example.naturediary

import androidx.compose.ui.graphics.Color

val md_theme_light_primary = Color(0xFF476810)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFdbd6b3)
val md_theme_light_secondaryContainer = Color(0xFFede7cc)
val md_theme_light_background = Color(0xFFFFF9E5) // A light yellow color
val md_theme_light_secondary = Color(0xFF388E3C) // A dark green color

val md_theme_dark_primary = Color(0xFFACD370)
val md_theme_dark_onPrimary = Color(0xFF213600)
val md_theme_dark_primaryContainer = Color(0xFF324F00)
val md_theme_dark_secondaryContainer = Color(0xFF324F00)
val md_theme_dark_background = Color(0xFF2C2F33) // A dark color for dark theme backgrounds
val md_theme_dark_secondary = Color(0xFF81C784) // A lighter green color for dark theme buttons