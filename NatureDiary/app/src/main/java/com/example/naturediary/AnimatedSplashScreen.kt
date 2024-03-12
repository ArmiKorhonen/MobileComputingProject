/**
 * File: AnimatedSplashScreen.kt
 *
 * Description: This Composable function implements the animated splash screen of the application. It utilizes
 * an Animatable float value to scale a logo with an animation effect. The animation enlarges the logo
 * to 120% of its size and then scales it back to its original size. After the animation sequence completes,
 * it navigates to the MainScreen. This screen serves as an engaging visual entry point for users when they
 * open the app, enhancing the overall user experience by providing a dynamic start before transitioning
 * to the app's main content.
 */

package com.example.naturediary.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import androidx.compose.ui.graphics.graphicsLayer
import com.example.naturediary.navigation.Screen
import com.example.naturediary.LeafLogo

@Composable
fun AnimatedSplashScreen(navController: NavController) {
    // Remember Animatable for scaling. This replaces the previous approach.
    val scale = remember { Animatable(1f) } // Start scale at 1f

    LaunchedEffect(key1 = true) {
        // Animate scale to become larger
        scale.animateTo(
            targetValue = 1.2f, // Increase scale to 120%
            animationSpec = tween(durationMillis = 1000) // Adjust duration as needed
        )
        // Animate scale to become slightly smaller
        scale.animateTo(
            targetValue = 1.0f, // Back to 100%
            animationSpec = tween(durationMillis = 500) // Adjust duration as needed
        )
        // Navigate after animation
        delay(500) // Wait for the last animation to complete
        navController.navigate(Screen.MainScreen.route) {
            popUpTo(Screen.AnimatedSplashScreen.route) { inclusive = true }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Leaf and logo at the top with scaling animation applied
        LeafLogo(modifier = Modifier.fillMaxSize()
                .graphicsLayer {
                    scaleX = scale.value
                    scaleY = scale.value
                }
        )
        Column(modifier = Modifier.weight(1f)) {
            // Additional UI elements for future use
        }
    }
}



