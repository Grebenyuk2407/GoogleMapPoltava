package com.example.googlemappoltava

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
    private lateinit var viewModel: MyViewModel
    private var routes: List<Routes>? = null // Изменено на List<Routes>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MyViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel.routeList.observe(viewLifecycleOwner) { routes ->
            this.routes = routes
            if (::mMap.isInitialized) {
                drawRoutes(routes)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        routes?.let { drawRoutes(it) } // Добавлено обновление маршрутов при готовности карты
    }

    private fun drawRoutes(routes: List<Routes>) {
        mMap.clear() // Очистим карту перед отрисовкой новых маршрутов

        routes.forEach { route ->
            val decodedPoints = PolyUtil.decode(route.overviewPolyline.points)
            val polylineOptions = PolylineOptions().addAll(decodedPoints).color(Color.RED).width(10f)
            mMap.addPolyline(polylineOptions)

            // Добавляем маркеры для начальной и конечной точек маршрута
            mMap.addMarker(MarkerOptions().position(decodedPoints.first()).title("Начальная точка"))
            val destinationName = route.overviewPolyline.points
            mMap.addMarker(MarkerOptions().position(decodedPoints.last()).title(destinationName))

            // Вычисляем границы маршрута
            val boundsBuilder = LatLngBounds.builder()
            decodedPoints.forEach { boundsBuilder.include(it) }
            val bounds = boundsBuilder.build()
            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 50)
            mMap.moveCamera(cameraUpdate)
        }

    }
}