package com.example.naturediary.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.naturediary.navigation.Screen

@Composable
fun MainScreen(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Nature Diary", style = MaterialTheme.typography.displayMedium, modifier = Modifier.padding(bottom = 8.dp))
        Button(onClick = { navController.navigate(Screen.AddEntryScreen.route) }, modifier = Modifier.padding(bottom = 8.dp)) {
            Text("Add New Entry")
        }
        Button(onClick = { navController.navigate(Screen.EntriesListScreen.route) }, modifier = Modifier.padding(bottom = 8.dp)) {
            Text("View Entries List")
        }
        Button(onClick = { navController.navigate(Screen.MapViewScreen.route) }) {
            Text("View Map")
        }
    }
}
