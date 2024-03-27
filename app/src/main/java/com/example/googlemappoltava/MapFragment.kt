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
import javax.inject.Inject

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var viewModel: MyViewModel
    private lateinit var destinationName: String
    private var routes: List<Routes>? = null

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
                drawRoutes(routes, destinationName)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        destinationName = arguments?.getString("destinationName").toString()
        routes?.let { drawRoutes(it, destinationName) }
    }

    private fun drawRoutes(routes: List<Routes>, destinationName: String) {
        mMap.clear()

        routes.forEach { route ->
            val decodedPoints = PolyUtil.decode(route.overviewPolyline.points)
            val polylineOptions = PolylineOptions().addAll(decodedPoints).color(Color.RED).width(10f)
            mMap.addPolyline(polylineOptions)


            mMap.addMarker(MarkerOptions().position(decodedPoints.first()).title("Начальная точка"))

            destinationName.let {
                mMap.addMarker(MarkerOptions().position(decodedPoints.last()).title(it))
            }


            val boundsBuilder = LatLngBounds.builder()
            decodedPoints.forEach { boundsBuilder.include(it) }
            val bounds = boundsBuilder.build()
            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 50)
            mMap.moveCamera(cameraUpdate)
        }

    }
}