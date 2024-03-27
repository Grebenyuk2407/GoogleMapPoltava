package com.example.googlemappoltava

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(private val apiInterface: ApiInterface)  : ViewModel() {

    private val _placesList = MutableLiveData<List<Results>>()
    val placesList: LiveData<List<Results>> = _placesList

    private val _routeList = MutableLiveData<List<Routes>>()
    val routeList: LiveData<List<Routes>> = _routeList

    fun loadPlaces() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = apiInterface.getNearbyPlaces()
            if (response.isSuccessful) {
                val places = response.body()?.results ?: emptyList()
                _placesList.postValue(places)
            }
        }
    }

    fun showMapWithRoute(
        destination: Results,
        fragmentManager: FragmentManager
    ) {
        viewModelScope.launch {
            val response = apiInterface.getComplexRoute(
                "49.5937300,34.5407300",
                "${destination.geometry.location.lat},${destination.geometry.location.lng}",
                ""
            )

            if (response.isSuccessful) {
                val routes = response.body()?.routes ?: emptyList()
                _routeList.postValue(routes)
                val mapFragment = MapFragment().apply {
                    arguments = Bundle().apply {
                        putString("destinationName", destination.name)
                    }
                }
                fragmentManager.beginTransaction()
                    .replace(R.id.mapContainer, mapFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}