package winkhanh.com.finalprojet

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.gms.location.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import winkhanh.com.finalprojet.fragments.MapsFragment
import winkhanh.com.finalprojet.fragments.ProfileFragment
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    lateinit var bottomNav : BottomNavigationView
    val fragmentManager: FragmentManager = supportFragmentManager

    var latitude: Double = 0.0
    var longitude: Double = 0.0
    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback
    var fragmentM : Fragment = ProfileFragment()
    val fragmentN : Fragment = ProfileFragment() //replace this for the Note Fragment
    val fragmentP : Fragment = ProfileFragment()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    fun subscribeLocationChange(){
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),1
            )
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment : Fragment = MapsFragment()
        supportFragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit()
        locationCallback = object: LocationCallback(){}
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.create()?.apply {

            interval = TimeUnit.SECONDS.toMillis(60)

            fastestInterval = TimeUnit.SECONDS.toMillis(30)

            maxWaitTime = TimeUnit.MINUTES.toMillis(2)

            priority = LocationRequest.PRIORITY_HIGH_ACCURACY


        }

        bottomNav = findViewById(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when (item.itemId) {
                R.id.action_map -> {
                    fragment = fragmentM
                    Log.d("Main","Pick Map")
                }
                R.id.action_note -> {
                    fragment = fragmentN
                    Log.d("Main","Pick Note")
                }
                R.id.action_profile -> {
                    fragment = fragmentP
                    Log.d("Main","Pick Profile")
                }
                else -> fragment = fragmentM
            }
            fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit()
            true
        }
        bottomNav.selectedItemId = R.id.action_map
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1){
            subscribeLocationChange()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates((locationCallback))
    }

    override fun onResume() {
        super.onResume()
        //subscribeLocationChange()
    }
}