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

    private val _routeList = MutableLiveData<List<Routes>>() // Заменили на List<Routes>
    val routeList: LiveData<List<Routes>> = _routeList // Заменили на List<Routes>

    fun loadPlaces(apiInterface: ApiInterface) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = apiInterface.getNearbyPlaces()
            if (response.isSuccessful) {
                val places = response.body()?.results ?: emptyList()
                _placesList.postValue(places)
            }
        }
    }

    private fun setRouteData(routes: List<Routes>) { // Изменено на List<Routes>
        _routeList.postValue(routes) // Изменено на List<Routes>
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
                val routes = response.body()?.routes ?: emptyList() // Изменено на List<Routes>
                setRouteData(routes) // Изменено на List<Routes>
                val mapFragment = MapFragment()
                fragmentManager.beginTransaction()
                    .replace(R.id.mapContainer, mapFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}