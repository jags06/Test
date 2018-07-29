package com.jags.arunkumar.androidtest.UI.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jags.arunkumar.androidtest.R
import com.jags.arunkumar.androidtest.UI.Activity.DeliveryActivity
import com.jags.arunkumar.androidtest.data.DeliveryData
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class MapFragment @Inject constructor() : DaggerFragment(),
    OnMapReadyCallback {
    private var mapView: MapView? = null
    private var gmap: GoogleMap? = null

    override fun onMapReady(googleMap: GoogleMap?) {
        gmap = googleMap
        gmap?.setMinZoomPreference(26F)
        gmap?.isIndoorEnabled = true
        val uiSettings = gmap?.uiSettings


        uiSettings?.isRotateGesturesEnabled = false
        uiSettings?.isScrollGesturesEnabled = false
        uiSettings?.isTiltGesturesEnabled = false
        uiSettings?.isZoomGesturesEnabled = false

        val b = arguments?.get("Key") as DeliveryData
        val ny = LatLng(b.locationData.lat, b.locationData.lng)

        val markerOptions = MarkerOptions()
        markerOptions.position(ny)
        gmap?.addMarker(markerOptions)

        gmap?.moveCamera(CameraUpdateFactory.newLatLngZoom(ny, 20F))

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as DeliveryActivity).setupTitleBar("Delivery Details")
        val view = inflater.inflate(R.layout.delivery_details, container, false)

        mapView = view.findViewById(R.id.map)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(MapFragment@ this)
        return view
    }

}