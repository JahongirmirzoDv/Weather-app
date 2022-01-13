package com.example.weatherapptest

import android.Manifest
import android.content.Intent
import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.example.weatherapp.models.Data

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.weatherapptest.databinding.ActivityMapsBinding
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.Marker
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener,
    GoogleMap.OnMarkerDragListener {

    private lateinit var binding: ActivityMapsBinding
    private val TAG = "MapsActivity"

    lateinit var googleApiClient: GoogleApiClient
    private lateinit var marker: Marker
    lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        window.clearFlags(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

        googleApiClient = getAPIClientInstance()
        if (googleApiClient != null) {
            googleApiClient.connect()
        }

        Dexter.withContext(this)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) { /* ... */
                    requestGPSSettings()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) { /* ... */
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) { /* ... */
                }
            }).check()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)



    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(41.2995, 69.2401), 4f))
        marker = googleMap.addMarker(
            MarkerOptions()
                .position(LatLng(41.2995, 69.2401))
                .draggable(true)
                .title("salom")
        )!!
        googleMap.setOnMarkerDragListener(this)

    }

    private fun getAPIClientInstance(): GoogleApiClient {
        return GoogleApiClient.Builder(this)
            .addApi(LocationServices.API).build()
    }


    private fun requestGPSSettings() {
        val locationRequest: LocationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        locationRequest.interval = 2000
        locationRequest.fastestInterval = 500
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)
        val result: PendingResult<LocationSettingsResult> =
            LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
        result.setResultCallback { result ->
            val status: Status = result.status
            when (status.statusCode) {
                LocationSettingsStatusCodes.SUCCESS -> {
                    Log.i("", "All location settings are satisfied.")
                    Toast.makeText(application, "GPS is already enable", Toast.LENGTH_SHORT)
                        .show()
                }
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                    Log.i(
                        "",
                        "Location settings are not satisfied. Show the user a dialog to" + "upgrade location settings "
                    )
                    try {
                        status.startResolutionForResult(this, 0x1)
                    } catch (e: IntentSender.SendIntentException) {
                        Log.e("Applicationsett", e.toString())
                    }
                }
                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                    Log.i(
                        "",
                        "Location settings are inadequate, and cannot be fixed here. Dialog " + "not created."
                    )
                    Toast.makeText(
                        application,
                        "Location settings are inadequate, and cannot be fixed here",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onMarkerDrag(marker: Marker) {

    }

    override fun onMarkerDragEnd(marker: Marker) {
        var data = Data()

        binding.button.visibility = View.VISIBLE
        Toast.makeText(
            this,
            "${marker.position.latitude} \n${marker.position.longitude}",
            Toast.LENGTH_SHORT
        ).show()
        binding.button.setOnClickListener {
            var intent = Intent(this, ViewActivity::class.java)
            intent.putExtra("data",
                com.example.weatherapp.models.AppLatLng(
                    marker.position.latitude.toString(),
                    marker.position.longitude.toString()
                )
            )
            startActivity(intent)
        }
    }

    override fun onMarkerDragStart(marker: Marker) {
    }

    override fun onMapClick(p0: LatLng) {

    }

}