package com.example.naturediary.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.naturediary.navigation.Screen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(navController: NavController) {
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.CAMERA,
            // Include WRITE_EXTERNAL_STORAGE if your app targets API level < 29
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    )

    LaunchedEffect(key1 = true) {
        Log.d("Permissions", "Requesting permissions...")
        permissionsState.launchMultiplePermissionRequest()
    }

    // Based on permissions state, show UI elements
    if (permissionsState.allPermissionsGranted) {
        Log.d("Permissions", "All permissions granted.")
        // Show button leading to AddEntryScreen only after all permissions are granted
    } else {
        // Show UI feedback that permissions are needed
        Log.d("Permissions", "Awaiting permissions.")
    }

    // Optionally, handle denied permissions with more user guidance

    Column(modifier = Modifier.padding(16.dp)) {

        Text(text = "Nature Diary", style = MaterialTheme.typography.displayMedium, modifier = Modifier.padding(bottom = 8.dp))
        Button(onClick = { navController.navigate(Screen.AddEntryScreen.route) }, modifier = Modifier.padding(bottom = 8.dp)) {
            Text("Add New Entry")
        }
        Button(onClick = { navController.navigate(Screen.EntriesListScreen.route) }, modifier = Modifier.padding(bottom = 8.dp)) {
            Text("View Entries List")
        }
        Button(onClick = { navController.navigate(Screen.MapViewScreen.route) }, modifier = Modifier.padding(bottom = 8.dp)) {
            Text("View Map")
        }
    }
}
