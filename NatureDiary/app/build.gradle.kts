plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id ("kotlin-kapt")
    id ("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.naturediary"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.naturediary"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2024.02.02"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.lifecycle:lifecycle-process:2.7.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.02"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    val nav_version = "2.7.7"

    implementation("androidx.navigation:navigation-compose:$nav_version")
    //implementation ("com.google.android.gms:play-services-maps:18.2.0")
    // Android Maps Compose composables for the Maps SDK for Android
    implementation ("com.google.maps.android:maps-compose:4.3.3")

    //Lifecycle
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

    //Google Services & Maps
    implementation ("com.google.android.gms:play-services-location:21.2.0")
    implementation ("com.google.maps.android:maps-compose:4.3.3")
    implementation ("com.google.android.gms:play-services-maps:18.2.0")

    //Accompanist (Permission)
    implementation ("com.google.accompanist:accompanist-permissions:0.34.0")

    //Hilt
    implementation ("com.google.dagger:hilt-android:2.51")
    kapt ("com.google.dagger:hilt-compiler:2.51")

    //Current location
    implementation ("com.google.android.gms:play-services-location:21.0.1")
    implementation ("androidx.compose.runtime:runtime-livedata:<compose_version>")

}

