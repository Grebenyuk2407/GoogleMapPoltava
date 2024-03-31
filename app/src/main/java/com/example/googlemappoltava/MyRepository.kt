package com.example.googlemappoltava

import android.util.Log


interface MyRepository {
    suspend fun getNearbyPlaces(): List<Results>?
    suspend fun getComplexRoute(origin: String, destination: String, waypoints: String): List<Routes>?
}

class MyRepositoryImpl(private val apiInterface: ApiInterface) : MyRepository {
    override suspend fun getNearbyPlaces(): List<Results>? {
        return try {
            val response = apiInterface.getNearbyPlaces()
            if (response.isSuccessful) {
                response.body()?.results
            } else {
                Log.e("MyRepositoryImpl", "getNearbyPlaces request failed: ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Log.e("MyRepositoryImpl", "getNearbyPlaces request failed", e)
            null
        }
    }

    override suspend fun getComplexRoute(origin: String, destination: String, waypoints: String): List<Routes>? {
        return try {
            val response = apiInterface.getComplexRoute(origin, destination, waypoints)
            if (response.isSuccessful) {
                response.body()?.routes
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}