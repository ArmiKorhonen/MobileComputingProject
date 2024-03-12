/**
 * File: EntriesListScreen.kt
 *
 * Description: This Composable function provides the UI for displaying a list of diary entries stored in the database.
 * It utilizes a Scaffold structure with a TopAppBar for navigation and titles. The core of the screen is a LazyColumn
 * that dynamically loads and displays each diary entry as an individual card. These cards are interactive, allowing users
 * to tap on an entry to navigate to a detailed view of that entry. The EntriesListViewModel is used to observe and fetch
 * the list of entries from the database, ensuring the UI updates reactively to data changes. This screen serves as a central
 * point for users to browse their diary entries and access specific details of each entry.
 */

package com.example.naturediary.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.naturediary.navigation.Screen
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.naturediary.EntriesListViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.naturediary.EntryItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntriesListScreen(navController: NavController, viewModel: EntriesListViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("List of Diary Entries") },
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
            EntriesListContent(navController = navController, viewModel = viewModel)
        }
    }
}

@Composable
fun EntriesListContent(navController: NavController, viewModel: EntriesListViewModel) {
    val entries by viewModel.entries.observeAsState(initial = emptyList())

    Column(modifier = Modifier.padding(16.dp)) {

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







