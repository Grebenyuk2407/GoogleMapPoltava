package com.example.googlemappoltava

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

    private val _routeData = MutableLiveData<Pair<String, String>>()
    val routeData: LiveData<Pair<String, String>> = _routeData

    fun loadPlaces(apiInterface: ApiInterface) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = apiInterface.getNearbyPlaces()
            if (response.isSuccessful) {
                val places = response.body()?.results ?: emptyList()
                _placesList.postValue(places)
            }
        }
    }

    private fun setRouteData(polylinePoints: String, destinationName: String) {
        _routeData.value = Pair(polylinePoints, destinationName)
    }

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
                val destinationName = destination.name
                points?.let {
                    setRouteData(it, destinationName)
                    val mapFragment = MapFragment()
                    fragmentManager.beginTransaction()
                        .replace(R.id.mapContainer, mapFragment)
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
    }
}