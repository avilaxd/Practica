package com.rudio.practice.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhoneNumber(val number: String) : Parcelable