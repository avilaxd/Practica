package com.rudio.practice.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.Group
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.*
import com.rudio.practice.R
import com.rudio.practice.ui.adapters.AdapterLocations
import com.rudio.practice.utils.LocationHelper
import com.rudio.practice.utils.PermissionsHelper
import com.rudio.practice.utils.ViewHelper
import com.rudio.practice.viewmodels.ViewModelTracking
import java.lang.Exception

class FragmentTracking : FragmentBase() {
    private lateinit var fusedLocation: FusedLocationProviderClient
    private lateinit var callback: LocationCallback
    private lateinit var groupTracking: Group
    private lateinit var viewModel: ViewModelTracking
    private lateinit var recyclerLocations: RecyclerView
    private val requestCodeLocation = 2000
    private val requestCodeSms = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelTracking(FragmentTrackingArgs.fromBundle(arguments!!).phoneNumber)
        fusedLocation = LocationServices.getFusedLocationProviderClient(requireContext())
        callback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult?.lastLocation != null) {
                    viewModel.addLocation(locationResult.lastLocation)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tracking, container, false)
        groupTracking = view.findViewById(R.id.groupTracking)
        recyclerLocations = view.findViewById(R.id.recyclerLocations)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        startObserving(viewModel)
        requestLocation()
    }

    override fun onPause() {
        fusedLocation.removeLocationUpdates(callback)
        super.onPause()
    }

    private fun requestLocation() {
        if (PermissionsHelper.hasPermissionLocation(requireContext())) {
            ViewHelper.setVisibility(groupTracking, true)
            fusedLocation.requestLocationUpdates(LocationHelper.getRequest(), callback, null)
        } else {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), requestCodeLocation)
        }
    }

    private fun sendSms() {
        if (PermissionsHelper.hasPermissionSms(requireContext())) {
            try {
                showDialog()
                val smsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(viewModel.getPhoneNumber().number, null, getString(R.string.fragment_tracking_sms_message), null, null)
                showToast(getString(R.string.fragment_tracking_sms_sent))
                dismissDialog()
            } catch (e: Exception) {
                dismissDialog()
                e.printStackTrace()
                showToast(getString(R.string.fragment_tracking_sms_not_sent))
            }
        } else {
            requestPermissions(arrayOf(Manifest.permission.SEND_SMS), requestCodeSms)
        }

    }

    private fun startObserving(viewModel: ViewModelTracking) {
        viewModel.getLocations().observe(viewLifecycleOwner, Observer {
            if (recyclerLocations.adapter == null) {
                recyclerLocations.adapter = AdapterLocations(it)
            } else {
                recyclerLocations.adapter?.notifyDataSetChanged()
            }
        })
        viewModel.getItMoved().observe(viewLifecycleOwner, Observer {
            if (it) {
                fusedLocation.removeLocationUpdates(callback)
                playSound()
                ViewHelper.setVisibility(groupTracking, false)
                sendSms()
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            requestCodeLocation -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestLocation()
                } else {
                    ViewHelper.setVisibility(groupTracking, false)
                    exitFragment(getString(R.string.fragment_tracking_error_location))
                }
            }
            requestCodeSms -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSms()
                } else {
                    exitFragment(getString(R.string.fragment_tracking_error_sms))
                }
            }
        }
    }

    private fun playSound() {
        val mediaPlayer = MediaPlayer.create(requireContext(), R.raw.tone)
        if (mediaPlayer.isPlaying) mediaPlayer.stop()
        mediaPlayer.setOnCompletionListener {
            it.release()
        }
        mediaPlayer.start()
    }
}