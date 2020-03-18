package com.rudio.practice.utils

import com.google.android.gms.location.LocationRequest

class LocationHelper {

    companion object {

        fun getRequest(): LocationRequest {
            val locationRequest = LocationRequest()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            locationRequest.interval = 5000
            locationRequest.fastestInterval = 5000
            return  locationRequest
        }
    }
}