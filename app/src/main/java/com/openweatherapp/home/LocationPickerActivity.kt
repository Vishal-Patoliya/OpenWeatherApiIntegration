package com.openweatherapp.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.gitprofile.db.AppDatabase
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.openweatherapp.R
import com.openweatherapp.model.CityModel
import kotlinx.android.synthetic.main.activity_location_picker.*
import java.io.IOException
import java.util.*

/**
 * @author Vishal
 * Use for Pick up location from Map for perticular book marked CITY.
 */
class LocationPickerActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener, View.OnClickListener {


    private lateinit var mMap: GoogleMap

    private val TAG: String = "LocationPickerActivity"
    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    private lateinit var marker: Marker

    private lateinit var dialog: BottomSheetDialog

    private lateinit var btnSaveLocation: Button
    private lateinit var btnCancelLocation: Button

    private lateinit var appDatabase: AppDatabase

    private var cityModel: CityModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_picker)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        init()
        process()
        setListener()
    }

    fun init() {

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        appDatabase = AppDatabase.getInstance(this)!!

    }

    fun process() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun setListener() {

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            android.R.id.home -> {
                finish()

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
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
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        enableMyLocationIfPermitted()

        mMap.setMinZoomPreference(7.0f)

        showDefaultLocation()

        mMap.setOnMapClickListener(this)

    }

    override fun onMapClick(point: LatLng?) {

        progressBar.visibility = View.VISIBLE

        if (point != null) {
            val cityName: String? = getAddress(this, point.latitude, point.longitude)

            if (cityName != null) {

                marker = mMap.addMarker(MarkerOptions()
                        .position(LatLng(point.latitude, point.longitude))
                        .anchor(0.5f, 0.1f).title(cityName))

                showBottomSheetDialogFragment()

            }

        }
    }

    /**
     * Getting Address From Lat and LNG and serialize pojo class for Book MArked City
     */
    private fun getAddress(context: Context, latitude: Double, longitude: Double): String? {

        var city: String? = null

        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses != null && addresses.size > 0) {

                val address = addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                city = addresses[0].locality
                val state = addresses[0].adminArea
                val country = addresses[0].countryName

                if (city != null) {
                    city = city
                } else if (state != null) {
                    city = state
                } else if (country != null) {
                    city = country
                }

                cityModel = CityModel()

                cityModel!!.cityName = city
                cityModel!!.address = address

                cityModel!!.lat = latitude.toLong()
                cityModel!!.lng = longitude.toLong()

            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {

            progressBar.visibility = View.GONE

        }
        return city
    }

    /**
     * Enable Location after getting permission from Device.
     */
    private fun enableMyLocationIfPermitted() {

        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE)

        } else if (mMap != null) {
            mMap.isMyLocationEnabled = true
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocationIfPermitted()
                } else {
                    showDefaultLocation()
                }
                return
            }
        }
    }

    /**
     * Showing Sydney Location
     */
    private fun showDefaultLocation() {
        val currentLocation = LatLng(22.782600, 73.600100)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
    }

    /**
     * Showing Bottom Sheet Dialog for saving location into DB.
     */
    private fun showBottomSheetDialogFragment() {
        val view = layoutInflater.inflate(R.layout.fragment_bottom_sheet_dialog, null)

        btnSaveLocation = view.findViewById(R.id.btnSaveLocation)
        btnCancelLocation = view.findViewById(R.id.btnCancelLocation)

        val tvCity: TextView = view.findViewById(R.id.tvCity)
        val tvAddress: TextView = view.findViewById(R.id.tvAddress)
        val tvLatLng: TextView = view.findViewById(R.id.tvLatLng)

        tvCity.text = cityModel!!.cityName
        tvAddress.text = cityModel!!.address
        tvLatLng.text = (getString(R.string.lattitude) + cityModel!!.lat.toString() + " , " +
                        getString(R.string.longitude) + cityModel!!.lng.toString())

        btnSaveLocation.setOnClickListener(this)
        btnCancelLocation.setOnClickListener(this)

        dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    override fun onClick(view: View?) {

        when (view?.id) {

            R.id.btnSaveLocation -> {

                appDatabase.wheatherAppDao().insertBookMarkCity(cityModel)
                dialog.dismiss()
                finish()

            }

            R.id.btnCancelLocation -> {
                marker.remove()
                mMap.clear()

                cityModel = null
                dialog.dismiss()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        if (mMap != null)
            mMap.clear()

    }

}