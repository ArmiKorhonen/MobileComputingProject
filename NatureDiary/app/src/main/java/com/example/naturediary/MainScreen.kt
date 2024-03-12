package com.example.naturediary.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.naturediary.LeafLogo
import com.example.naturediary.R
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


}
