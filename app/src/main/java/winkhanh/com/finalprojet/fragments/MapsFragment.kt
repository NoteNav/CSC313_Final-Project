package winkhanh.com.finalprojet.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.parse.FindCallback
import com.parse.Parse
import com.parse.ParseException
import com.parse.ParseQuery
import winkhanh.com.finalprojet.MainActivity
import winkhanh.com.finalprojet.R
import winkhanh.com.finalprojet.models.Post

class MapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        getLocation(googleMap)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun fetchPostsNear(lat: Double, lon: Double, f: FindCallback<Post>){
        val query : ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        query.include(Post.KEY_USER)
        query.whereGreaterThanOrEqualTo(Post.KEY_LAT,lat-2)
        query.whereGreaterThanOrEqualTo(Post.KEY_LONG,lon-2)
        query.whereLessThanOrEqualTo(Post.KEY_LAT, lat+2)
        query.whereLessThanOrEqualTo(Post.KEY_LONG,lon+2)
        query.findInBackground(f)
    }

    private fun publishPosts(lat: Double, lon: Double, googleMap: GoogleMap){
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(lat, lon)))
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(20.0F))
        fetchPostsNear(lat, lon, object: FindCallback<Post>{
            override fun done(objects: MutableList<Post>?, e: ParseException?) {
                if (e!=null){
                    Log.e("Map","fetching fail")
                    return
                }
                Log.d("Map",objects.toString())
                objects?.forEach { post ->
                    val location = post.location
                    val pos = LatLng(location.first as Double, location.second as Double)
                    googleMap.addMarker(MarkerOptions().position(pos).title(post.title))
                }
            }

        })
    }

    private fun getLocation(googleMap: GoogleMap){
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED && context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("Map", "require location")
            ActivityCompat.requestPermissions(
                context as MainActivity,
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

        (context as MainActivity).fusedLocationClient.lastLocation.addOnSuccessListener {
            if (it==null){
                return@addOnSuccessListener
            }
            Log.d("Map",it.toString())
            publishPosts(it.latitude, it.longitude, googleMap)

        }
    }

}