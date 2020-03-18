package com.rudio.practice.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class PermissionsHelper {

    companion object {

        fun hasPermissionLocation(context: Context): Boolean {
            return ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        }

        fun hasPermissionSms(context: Context): Boolean {
            return ActivityCompat.checkSelfPermission(context,
                Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED
        }
    }
}