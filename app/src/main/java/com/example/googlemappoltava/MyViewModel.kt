package com.example.googlemappoltava

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    private val _placesList = MutableLiveData<List<Results>>()
    val placesList: LiveData<List<Results>> = _placesList

    // Функция для загрузки мест
    fun loadPlaces(apiInterface: ApiInterface) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = apiInterface.getNearbyPlaces()
            if (response.isSuccessful) {
                val places = response.body()?.results ?: emptyList()
                _placesList.postValue(places)
            }
        }
    }

    // Функция для отображения карты с маршрутом
    fun showMapWithRoute(
        destination: Results,
        apiInterface: ApiInterface,
        fragmentManager: FragmentManager
    ) {
        viewModelScope.launch {
            val response = apiInterface.getComplexRoute(
                "49.5937300,34.5407300",
                "${destination.geometry.location.lat},${destination.geometry.location.lng}",
                ""
            )

            if (response.isSuccessful) {
                val points = response.body()?.routes?.firstOrNull()?.overviewPolyline?.points
                points?.let {
                    val bundle = Bundle().apply {
                        putString("polylinePoints", it)
                        putString("destinationName", destination.name)
                    }
                    val mapFragment = MapFragment().apply { arguments = bundle }

                    fragmentManager.beginTransaction()
                        .replace(R.id.mapContainer, mapFragment)
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
    }
}