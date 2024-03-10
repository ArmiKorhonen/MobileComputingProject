package com.example.naturediary.screens

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.padding

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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.TextField
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.naturediary.ui.theme.NatureDiaryTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.example.naturediary.LocationViewModel
import androidx.compose.runtime.livedata.observeAsState




/*@Composable
fun AddEntryScreen(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {

    }
}*/




@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddEntryScreen(navController: NavController, locationViewModel: LocationViewModel = viewModel()) {
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    LaunchedEffect(key1 = true) {
        permissionsState.launchMultiplePermissionRequest()
    }

    if (permissionsState.allPermissionsGranted) {
        // Permissions are granted, now you can fetch the location
        locationViewModel.fetchLastLocation()
    } else {
        // Handle permissions not granted case
        // For simplicity, not shown here. Implement as needed.
    }

    NatureDiaryTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            // Replace the direct call to LocationSuccessUI with AddEntryScreenContent
            AddEntryScreenContent(navController= navController, locationViewModel = locationViewModel)
        }
    }
}


@Composable
fun AddEntryScreenContent(navController: NavController, locationViewModel: LocationViewModel) {
    val locationPair = locationViewModel.locationData.observeAsState().value
    val address = locationViewModel.addressData.observeAsState().value

    Column(modifier = Modifier.fillMaxSize()) {
        // Other UI elements here
        // For example, a text input for notes
        Text(text = "Add Entry", style = MaterialTheme.typography.displayMedium, modifier = Modifier.padding(bottom = 8.dp))
        Button(onClick = { navController.navigateUp() }) {
            Text("Back to Main Screen")
        }

        // Map occupies the remaining space after other elements have taken theirs
        locationPair?.let { pair ->
            val location = LatLng(pair.first, pair.second)
            // This ensures fetchAddress is called whenever the location updates.
            LaunchedEffect(location) {
                locationViewModel.fetchAddress(location)
            }
            Box(modifier = Modifier.size(300.dp, 200.dp)) {
                LocationSuccessUI(location = location)
            }
        }

        // Display the address
        address?.let {
            Text(text = it, modifier = Modifier.padding(16.dp))
        }

        TextField(
            value = "Your note here...",
            onValueChange = { /* Handle text change */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
fun LocationSuccessUI(location: LatLng, modifier: Modifier = Modifier) {
    GoogleMap(
        modifier = modifier.fillMaxSize(), // Ensure the map fills the Box
        cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(location, 15f)
        }
    ) {
        Marker(
            state = MarkerState(position = location),
            title = "Current Location",
            snippet = "You are here"
        )
    }
}






