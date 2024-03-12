/**
 * File: NatureDiaryApp.kt
 *
 * Description: Defines the base class for the Nature Diary application, extending the Android Application class.
 * It is annotated with @HiltAndroidApp, enabling dependency injection throughout the app using Dagger-Hilt. This setup
 * is crucial for managing dependencies in a scalable, efficient, and testable manner. The annotation triggers Hilt's
 * code generation, creating a Dagger component graph that spans the application's lifecycle. Custom application setup
 * and initialization logic can be added within this class if needed, providing a centralized point for application-wide
 * configurations and services.
 */

package com.example.naturediary

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NatureDiaryApp : Application() {
    // Custom application setup can go here
}
