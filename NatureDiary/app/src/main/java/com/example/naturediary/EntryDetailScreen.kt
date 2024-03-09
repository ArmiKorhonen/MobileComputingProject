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

@Composable
fun EntryDetailScreen(entryId: String?, navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Entry Detail", style = MaterialTheme.typography.displayMedium, modifier = Modifier.padding(bottom = 8.dp))
        Button(onClick = { navController.navigateUp() }) {
            Text("Back")
        }
    }
}


