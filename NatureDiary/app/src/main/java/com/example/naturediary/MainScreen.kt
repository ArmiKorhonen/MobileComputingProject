/**
 * File: MainScreen.kt
 *
 * Description: The MainScreen of the application, implemented as a Composable function. It is responsible
 * for requesting the necessary permissions from the user (internet and location) at runtime and directing
 * the user to either the AddEntryScreen or EntriesListScreen based on their selection. The screen
 * displays a leaf logo and provides a UI feedback mechanism if permissions are not granted.
 */

package com.example.naturediary.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.naturediary.LeafLogo
import com.example.naturediary.navigation.Screen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(navController: NavController) {
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
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
        Column(modifier = Modifier.fillMaxSize()) {
            // Leaf and logo at the top with scaling animation applied
            LeafLogo(modifier = Modifier.weight(1f))
            Column(modifier = Modifier.weight(1f).fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = { navController.navigate(Screen.AddEntryScreen.route) }, modifier = Modifier.padding(bottom = 8.dp)) {
                    Text("Add New Entry", style = MaterialTheme.typography.titleLarge)
                }
                Button(onClick = { navController.navigate(Screen.EntriesListScreen.route) }, modifier = Modifier.padding(bottom = 8.dp)) {
                    Text("View Entries List", style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    } else {
        // Show UI feedback that permissions are needed
        Log.d("Permissions", "Awaiting permissions.")
        Column(modifier = Modifier.fillMaxSize()) {
            // Leaf and logo at the top with scaling animation applied
            LeafLogo(modifier = Modifier.weight(1f))
            Column(modifier = Modifier.weight(1f).fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text("You need to give permissions for location and internet in order to use this app.", style = MaterialTheme.typography.titleLarge)
            }
        }
    }

}
