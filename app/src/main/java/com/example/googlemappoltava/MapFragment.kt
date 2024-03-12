package com.example.googlemappoltava

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var polylinePoints: String? = null
    private var destinationName: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        polylinePoints = arguments?.getString("polylinePoints")
        destinationName = arguments?.getString("destinationName") // Получаем название места
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        drawPolyline(polylinePoints, destinationName)
    }

    private fun drawPolyline(polylinePoints: String?, destinationName: String?) {
        // Теперь у нас есть название места, которое можно использовать для маркера
        polylinePoints?.let { it ->
            val decodedPoints = PolyUtil.decode(it)
            val polylineOptions = PolylineOptions().addAll(decodedPoints).color(Color.RED).width(10f)
            mMap.addPolyline(polylineOptions)

            // Показать начальную точку маршрута
            mMap.addMarker(MarkerOptions().position(decodedPoints.first()).title("Start"))

            // Показать конечную точку маршрута с названием места
            destinationName?.let {
                mMap.addMarker(MarkerOptions().position(decodedPoints.last()).title(it))
            }

            // Установить камеру так, чтобы она показывала весь маршрут
            val boundsBuilder = LatLngBounds.builder()
            decodedPoints.forEach { boundsBuilder.include(it) }
            val bounds = boundsBuilder.build()
            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 50)
            mMap.moveCamera(cameraUpdate)
        }
    }
}