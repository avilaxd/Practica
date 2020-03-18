package com.rudio.practice.ui.adapters.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rudio.practice.R
import com.rudio.practice.data.LocationModel

class HolderLocation(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val textPosition  = itemView.findViewById<TextView>(R.id.textPosition)
    private val textLatitude = itemView.findViewById<TextView>(R.id.textLatitude)
    private val textLongitude = itemView.findViewById<TextView>(R.id.textLongitude)

    fun onBind(locationModel: LocationModel) {
        textPosition.text = adapterPosition.toString()
        textLatitude.text = locationModel.latitude.toString()
        textLongitude.text = locationModel.longitude.toString()
    }
}