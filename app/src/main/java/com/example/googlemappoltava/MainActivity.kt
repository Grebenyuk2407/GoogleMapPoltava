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
    @Inject lateinit var apiInterface: ApiInterface
    @Inject lateinit var viewModel: MyViewModel
    private lateinit var placesAdapter: PlacesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        placesRecyclerView = findViewById(R.id.placesRecyclerView)
        apiInterface = Client.client.create(ApiInterface::class.java)
        viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        placesRecyclerView.layoutManager = LinearLayoutManager(this)
        placesAdapter = PlacesAdapter { selectedPlace ->
            viewModel.showMapWithRoute(selectedPlace, apiInterface, supportFragmentManager)
        }
        placesRecyclerView.adapter = placesAdapter

        viewModel.loadPlaces(apiInterface)

        viewModel.placesList.observe(this) { places ->
            placesAdapter.submitList(places)
        }
    }
}