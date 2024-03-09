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
fun EntriesListScreen(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Entries List", style = MaterialTheme.typography.displayMedium, modifier = Modifier.padding(bottom = 8.dp))
        Button(onClick = { navController.navigateUp() }, modifier = Modifier.padding(bottom = 8.dp)) {
            Text("Back to Main Screen")
        }
        // Example button for navigating to EntryDetailScreen
        Button(onClick = { navController.navigate(Screen.EntryDetailScreen.createRoute(entryId = "exampleEntryId")) }) {
            Text("View Entry Detail (Example)")
        }
    }
}


