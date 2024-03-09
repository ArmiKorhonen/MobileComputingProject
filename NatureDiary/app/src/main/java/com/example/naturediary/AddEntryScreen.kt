package com.example.naturediary.screens

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.naturediary.LocationMapVM
import android.Manifest
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import android.provider.Settings
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.naturediary.PermissionEvent
import com.example.naturediary.ViewState
import com.example.naturediary.hasLocationPermission
import com.example.naturediary.ui.theme.NatureDiaryTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import dagger.hilt.android.AndroidEntryPoint

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddEntryScreen(navController: NavController, locationViewModel: LocationMapVM = viewModel()) {
    // Permissions State
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    // Observe location view state
    val viewState by locationViewModel.viewState.collectAsStateWithLifecycle()

    // Request permissions
    LaunchedEffect(key1 = true) {
        permissionState.launchMultiplePermissionRequest()
    }

    NatureDiaryTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            // Your permission handling and UI rendering based on ViewState
            when (viewState) {
                ViewState.Loading -> LoadingUI()
                ViewState.RevokedPermissions -> PermissionRevokedUI {
                    permissionState.launchMultiplePermissionRequest()
                }
                is ViewState.Success -> (viewState as ViewState.Success).location?.let { location ->
                    LocationSuccessUI(location, locationViewModel)
                }
            }
        }
    }
}

@Composable
fun LoadingUI() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun PermissionRevokedUI(onTryAgainClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("We need permissions to use this app")
        Button(onClick = onTryAgainClicked) {
            Text("Try Again")
        }
    }
}

@Composable
fun LocationSuccessUI(location: LatLng, locationViewModel: LocationMapVM) {
    // Remember camera position state and center on the user's current location
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 15f) // Use dynamic location
    }

    // Display GoogleMap with the centered position and a marker at the user's location
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = location),
            title = "Current Location",
            snippet = "You are here"
        )
    }
}




/*@Composable
fun AddEntryScreen(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Add Entry", style = MaterialTheme.typography.displayMedium, modifier = Modifier.padding(bottom = 8.dp))
        Button(onClick = { navController.navigateUp() }) {
            Text("Back to Main Screen")
        }
    }
}*/


