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
    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)
    val locationData = MutableLiveData<Pair<Double, Double>?>()

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
                locationData.value = null
            }
        }
    }

    val addressData = MutableLiveData<String>()

    @Suppress("DEPRECATION")
    fun fetchAddress(location: LatLng) {
        viewModelScope.launch {
            val geocoder = Geocoder(getApplication(), Locale.getDefault())
            // Use try-catch to handle potential IOException
            try {
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    val address = addresses[0]
                    val addressFragments = with(address) {
                        (0..maxAddressLineIndex).map { getAddressLine(it) }
                    }

                    addressData.postValue(addressFragments.joinToString(separator = "\n"))
                } else {
                    addressData.postValue("No address found")
                }
            } catch (e: IOException) {
                // Handle IOException, potentially due to network issues
                addressData.postValue("Error fetching address")
            }
        }
    }

}
