package com.example.naturediary.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier

import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun MapViewScreen(navController: NavController) {
    val oulu = LatLng(65.0121, 25.4651)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(oulu, 15f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = oulu),
            title = "Oulu",
            snippet = "Marker in Oulu"
        )
    }
}



