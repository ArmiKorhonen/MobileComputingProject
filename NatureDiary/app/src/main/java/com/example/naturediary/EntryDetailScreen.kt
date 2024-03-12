package com.example.naturediary.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.naturediary.DiaryEntry
import com.example.naturediary.EntriesListViewModel
import com.google.android.gms.maps.model.LatLng
import com.example.naturediary.EntryItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryDetailScreen(entryId: String?, navController: NavController, viewModel: EntriesListViewModel = hiltViewModel()) {
    // Check if entryId is not null and can be converted to Int, else navigate back
    val id = entryId?.toIntOrNull() ?: run {
        navController.popBackStack()
        return
    }

    val entry by viewModel.getEntryById(id).observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Diary Entry for ") },
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
            entry?.let { diaryEntry ->
                EntryDetailContent(diaryEntry = diaryEntry)
            } ?: run {
                // Handle case where entry is not found by showing a message or navigating back
            }
        }
    }

}

@Composable
fun EntryDetailContent(diaryEntry: DiaryEntry) {

    Column(modifier = Modifier.padding(16.dp)) {

        val location = LatLng(diaryEntry.latitude, diaryEntry.longitude)
        LocationSuccessUI(location = location, modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(10.dp)))

        Spacer(modifier = Modifier.height(16.dp))

        EntryItem(entry = diaryEntry)

        Text(text = "${diaryEntry.note}", style = MaterialTheme.typography.bodyMedium)


    }
}



