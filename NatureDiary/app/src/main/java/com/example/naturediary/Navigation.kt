/**
 * File: Navigation.kt
 *
 * Description: Defines the navigation architecture of the application using a sealed class named Screen.
 * Each object within Screen represents a distinct screen in the app, encapsulating the routing path as a
 * property. This setup facilitates type-safe navigation throughout the app. The EntryDetailScreen includes
 * a dynamic route that incorporates an entry ID, demonstrating how to handle parameterized navigation to
 * support displaying details for a specific diary entry. This structured approach to navigation simplifies
 * the process of moving between different parts of the application and passing data between screens.
 */

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
