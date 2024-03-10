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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import android.util.Log


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddEntryScreen(navController: NavController, locationViewModel: LocationViewModel = viewModel()) {

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

    locationViewModel.fetchLastLocation()

    Log.d("Nature Diary", "Location Pair: $locationPair")

    Column(modifier = Modifier.fillMaxSize()) {
        // Other UI elements here
        // For example, a text input for notes
        Text(text = "Add New Entry", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(16.dp))
        Button(modifier = Modifier.padding(6.dp), onClick = { navController.navigateUp() }) {
            Text("Back to Main Screen")
        }

        // Map occupies the remaining space after other elements have taken theirs
        locationPair?.let { pair ->
            val location = LatLng(pair.first, pair.second)

            Log.d("Nature Diary", "Location for map: $location")

            // This ensures fetchAddress is called whenever the location updates.
            LaunchedEffect(location) {
                locationViewModel.fetchAddress(location)
            }
            Box(modifier = Modifier.fillMaxWidth().height(200.dp)
                .padding(16.dp).clip(RoundedCornerShape(10.dp))) {
                LocationSuccessUI(location = location)
            }
        }

        // Display the address
        address?.let {
            Log.d("Nature Diary", "Address: $it")
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
    Log.d("Nature Diary", "Displaying map at location: $location")
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







