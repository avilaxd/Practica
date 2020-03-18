package com.rudio.practice.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rudio.practice.R
import com.rudio.practice.data.LocationModel
import com.rudio.practice.ui.adapters.holders.HolderLocation

class AdapterLocations(
    private val locationModels: MutableList<LocationModel>
) : RecyclerView.Adapter<HolderLocation>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderLocation {
        return HolderLocation(LayoutInflater.from(parent.context)
            .inflate(R.layout.holder_location, parent, false))
    }

    override fun getItemCount(): Int {
        return locationModels.size
    }

    override fun onBindViewHolder(holder: HolderLocation, position: Int) {
        holder.onBind(locationModels[position])
    }
}