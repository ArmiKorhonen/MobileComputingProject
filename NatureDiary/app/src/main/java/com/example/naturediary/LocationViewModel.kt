/**
 * File: LocationViewModel.kt
 *
 * Description: This ViewModel is responsible for fetching the user's current location and
 * reverse geocoding it to obtain a human-readable address. It interacts with the FusedLocationProvider
 * for location services and utilizes the Geocoder class for reverse geocoding. LiveData is used
 * to observe location and address data within the UI components.
 */

package com.example.naturediary

import android.app.Application
import android.location.Geocoder
import java.util.Locale
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import java.io.IOException

class LocationViewModel(application: Application) : AndroidViewModel(application) {
    // FusedLocationProviderClient is used for accessing the device's last known location.
    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)

    // LiveData to hold the latest location data as a Pair of latitude and longitude.
    val locationData = MutableLiveData<Pair<Double, Double>?>()

    /**
     * Fetches the device's last known location and updates locationData.
     * This method should be called with the necessary location permissions granted.
     * It attempts to access the last known location from the FusedLocationProviderClient.
     * If successful, it updates locationData with the new location; if not, it sets locationData to null.
     */
    fun fetchLastLocation() {
        viewModelScope.launch {
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        locationData.value = Pair(location.latitude, location.longitude)
                    } else {
                        locationData.value = null
                    }
                }
            } catch (e: SecurityException) {
                // This catch block handles cases where the app lacks the necessary permissions,
                // setting the location data to null to indicate the lack of access.
                locationData.value = null
            }
        }
    }

    // LiveData to hold the address obtained through reverse geocoding.
    val addressData = MutableLiveData<String>()

    /**
     * Performs reverse geocoding to find a human-readable address from a given LatLng location.
     * Updates addressData with the resulting address, if found. In case of failure, it updates
     * addressData with a relevant error message.
     *
     * @param location The LatLng object containing the latitude and longitude to be reverse geocoded.
     */
    @Suppress("DEPRECATION")
    fun fetchAddress(location: LatLng) {
        viewModelScope.launch {
            val geocoder = Geocoder(getApplication(), Locale.getDefault())
            try {
                // Attempt to get an address for the given location. This might not succeed if
                // there's no internet connection, or if the Geocoder service is not available.
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    val address = addresses[0]
                    // Compile the address lines into a single string.
                    val addressFragments = with(address) {
                        (0..maxAddressLineIndex).map { getAddressLine(it) }
                    }

                    addressData.postValue(addressFragments.joinToString(separator = "\n"))
                } else {
                    addressData.postValue("No address found")
                }
            } catch (e: IOException) {
                // Handles cases where geocoding fails, such as due to network issues.
                addressData.postValue("Error fetching address")
            }
        }
    }
}

