package com.example.naturediary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.naturediary.ui.theme.NatureDiaryTheme

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.naturediary.navigation.Screen

import com.example.naturediary.screens.MainScreen
import com.example.naturediary.screens.AddEntryScreen
import com.example.naturediary.screens.EntriesListScreen
import com.example.naturediary.screens.EntryDetailScreen
import com.example.naturediary.screens.MapViewScreen
import dagger.hilt.android.AndroidEntryPoint


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(Screen.MainScreen.route) {
            MainScreen(navController)
        }
        composable(Screen.AddEntryScreen.route) {
            AddEntryScreen(
                navController)
        }
        composable(Screen.EntriesListScreen.route) {
            EntriesListScreen(navController)
        }
        composable(Screen.EntryDetailScreen.route) { backStackEntry ->
            // Extract the entryId from the arguments
            val entryId = backStackEntry.arguments?.getString("entryId")
            EntryDetailScreen(entryId, navController)
        }
        composable(Screen.MapViewScreen.route) {
            MapViewScreen(navController)
        }
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}
