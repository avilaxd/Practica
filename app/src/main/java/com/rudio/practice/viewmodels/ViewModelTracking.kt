package com.rudio.practice.viewmodels

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rudio.practice.data.LocationModel
import com.rudio.practice.data.PhoneNumber

//Does not extend ViewModel() class in order to finish the project as soon as possible.
class ViewModelTracking(private val phoneNumber: PhoneNumber) {
    private val locations = MutableLiveData<MutableList<LocationModel>>()
    private var lastLocation: Location? = null
    private val itMoved = MutableLiveData<Boolean>()

    init {
        locations.value = mutableListOf()
    }

    fun getLocations(): LiveData<MutableList<LocationModel>> {
        return locations
    }

    fun getItMoved() = itMoved

    fun addLocation(location: Location) {
        locations.value?.add(transformToLocationModel(location))
        locations.value = locations.value
        if (lastLocation == null) {
            lastLocation = location
        } else {
            val distance = lastLocation?.distanceTo(location) ?: 0F
            if (distance >= 1F) {
                itMoved.value = true
            }
        }
    }

    private fun transformToLocationModel(location: Location): LocationModel {
        return LocationModel(location.latitude, location.longitude)
    }

    fun getPhoneNumber(): PhoneNumber = phoneNumber
}