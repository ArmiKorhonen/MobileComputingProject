package com.example.naturediary.navigation

sealed class Screen(val route: String) {
    object AnimatedSplashScreen : Screen("animatedSplashScreen")
    object MainScreen : Screen("mainScreen")
    object AddEntryScreen : Screen("addEntryScreen")
    object EntriesListScreen : Screen("entriesListScreen")
    object EntryDetailScreen : Screen("entryDetailScreen/{entryId}") {
        fun createRoute(entryId: String) = "entryDetailScreen/$entryId"
    }
    object MapViewScreen : Screen("mapViewScreen")
}
