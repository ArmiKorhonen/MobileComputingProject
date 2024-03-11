package com.example.naturediary.screens

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.naturediary.DiaryEntry
import com.example.naturediary.EntriesListViewModel
import java.text.SimpleDateFormat
import java.util.*
import androidx.hilt.navigation.compose.hiltViewModel



@Composable
fun EntriesListScreen(navController: NavController, viewModel: EntriesListViewModel = hiltViewModel()) {
    val entries by viewModel.entries.observeAsState(initial = emptyList())

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Entries List", style = MaterialTheme.typography.displayMedium, modifier = Modifier.padding(bottom = 8.dp))
        Button(onClick = { navController.navigateUp() }, modifier = Modifier.padding(bottom = 8.dp)) {
            Text("Back to Main Screen")
        }

        LazyColumn {
            items(entries, key = { entry ->
                entry.id // Use the unique ID as key for each item
            }) { entry ->
                EntryItem(entry = entry, onClick = {
                    navController.navigate(Screen.EntryDetailScreen.createRoute(entryId = entry.id.toString()))
                })
            }
        }
    }
}


@Composable
fun EntryItem(entry: DiaryEntry, onClick: () -> Unit) {
    val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
    Card(modifier = Modifier.padding(vertical = 4.dp).clickable(onClick = onClick)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Date: ${sdf.format(Date(entry.timestamp))}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Address: ${entry.address}", style = MaterialTheme.typography.bodySmall)
        }
    }
}


