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
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.room.Room
import com.example.naturediary.DiaryEntry
import com.example.naturediary.DiaryEntryDatabase
import com.example.naturediary.EntriesListViewModel
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL
import org.json.JSONObject
import kotlinx.coroutines.*
import com.example.naturediary.temperatureColor
import com.example.naturediary.EntryItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEntryScreen(navController: NavController, locationViewModel: LocationViewModel = viewModel(), entriesViewModel: EntriesListViewModel = hiltViewModel()) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Diary Entry") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        androidx.compose.material3.Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            //Page content here
            AddEntryScreenContent(navController= navController, locationViewModel = locationViewModel, entriesViewModel = entriesViewModel)

        }
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEntryScreenContent(navController: NavController, locationViewModel: LocationViewModel, entriesViewModel: EntriesListViewModel) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Use a rememberSaveable state to survive configuration changes
    var noteText by rememberSaveable { mutableStateOf("") }
    var showConfirmationDialog by remember { mutableStateOf(false) }

    val locationPair = locationViewModel.locationData.observeAsState().value
    val address = locationViewModel.addressData.observeAsState().value
    var temperature by remember { mutableStateOf<Double?>(null) }
    val currentDate = System.currentTimeMillis()
    val dateWithoutTime = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val currentDateTime = dateWithoutTime.format(Date(currentDate))

    locationViewModel.fetchLastLocation()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        locationPair?.let { pair ->
            val location = LatLng(pair.first, pair.second)
            LaunchedEffect(location) {
                locationViewModel.fetchAddress(location)
                fetchWeatherData(location.latitude, location.longitude) { temp ->
                    temperature = temp // Update the temperature state
                }

            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(10.dp))) {
                LocationSuccessUI(location = location)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        EntryItem(
            entry = DiaryEntry(
                id = 0, // Temporary ID for UI purposes, since this is not a saved entry yet
                note = "", // This will be empty since it's not used in this context
                latitude = 0.0, // This will be empty since it's not used in this context
                longitude = 0.0, // This will be empty since it's not used in this context
                address = address ?: "Address not available",
                timestamp = currentDate,
                temperature = temperature ?: 0.0 // Replace with actual temperature
            ),
            onClick = null // No click action needed here
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer),
            value = noteText,
            onValueChange = { noteText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            label = { Text("Your note") }
        )

        // Save entry button
        Button(
            onClick = {
                scope.launch {
                    // Assuming you have a way to get an instance of your database
                    //val db = DiaryEntryDatabase.getDatabase(context)
                    val diaryEntry = DiaryEntry(
                        note = noteText,
                        latitude = locationPair?.first ?: 0.0,
                        longitude = locationPair?.second ?: 0.0,
                        address = address ?: "Address not available",
                        timestamp = System.currentTimeMillis(),
                        temperature = temperature ?: 0.0 //Default value
                    )
                    entriesViewModel.insertEntry(diaryEntry)
                    // Set the flag to show the confirmation dialog
                    showConfirmationDialog = true
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Save Entry")
        }






        if (showConfirmationDialog) {
            AlertDialog(
                onDismissRequest = { showConfirmationDialog = false },
                title = { Text("Entry Saved") },
                text = { Text("Your entry has been saved successfully.") },
                confirmButton = {
                    Button(
                        onClick = {
                            showConfirmationDialog = false
                            navController.navigateUp() // Navigate back to the main screen
                        }) {
                        Text("OK")
                    }
                }
            )
        }
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

fun fetchWeatherData(latitude: Double, longitude: Double, onResult: (Double?) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        val url = URL("https://api.open-meteo.com/v1/forecast?latitude=$latitude&longitude=$longitude&current=temperature_2m,weather_code")
        val urlConnection = url.openConnection() as HttpURLConnection
        try {
            val data = urlConnection.inputStream.bufferedReader().readText()
            val jsonObject = JSONObject(data)
            val currentWeather = jsonObject.getJSONObject("current")
            val temperature = currentWeather.getDouble("temperature_2m")
            withContext(Dispatchers.Main) {
                onResult(temperature) // Call the callback with the temperature
            }
        } finally {
            urlConnection.disconnect()
        }
    }
}




