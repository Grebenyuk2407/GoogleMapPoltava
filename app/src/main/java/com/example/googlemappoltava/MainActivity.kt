package com.example.googlemappoltava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var placesRecyclerView: RecyclerView
    private lateinit var viewModel: MyViewModel
    private lateinit var placesAdapter: PlacesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        placesRecyclerView = findViewById(R.id.placesRecyclerView)
        placesRecyclerView.layoutManager = LinearLayoutManager(this)
        placesAdapter = PlacesAdapter { selectedPlace ->
            viewModel.showMapWithRoute(selectedPlace, supportFragmentManager)
        }
        placesRecyclerView.adapter = placesAdapter

        viewModel.loadPlaces()

        viewModel.placesList.observe(this) { places ->
            placesAdapter.submitList(places)
        }
    }
}