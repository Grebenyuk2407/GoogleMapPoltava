package com.example.googlemappoltava


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
                null
            }
        } catch (e: Exception) {
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