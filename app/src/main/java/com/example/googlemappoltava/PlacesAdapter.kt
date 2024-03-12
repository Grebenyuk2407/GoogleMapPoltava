package com.example.googlemappoltava

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class PlacesAdapter(private val onItemClick: (Results) -> Unit) :
    ListAdapter<Results, PlacesAdapter.PlaceViewHolder>(PlaceDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.list_item_place, parent, false)
        return PlaceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place = getItem(position)
        holder.bind(place)
        holder.itemView.setOnClickListener { onItemClick(place) }
    }

    inner class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val placeNameTextView: TextView = itemView.findViewById(R.id.placeNameTextView)
        private val routeButton: ImageButton = itemView.findViewById(R.id.routeButton)

        fun bind(place: Results) {
            placeNameTextView.text = place.name
            routeButton.setOnClickListener { onItemClick(place) }
        }
    }

    private class PlaceDiffCallback : DiffUtil.ItemCallback<Results>() {
        override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem == newItem
        }
    }
}