package com.example.naturediary.screens

import androidx.compose.animation.core.Animatable
import com.example.naturediary.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import kotlinx.coroutines.delay
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
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



