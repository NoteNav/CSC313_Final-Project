package winkhanh.com.finalprojet.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
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

         */
        Log.d("Map","Map Ready")

        (context as MainActivity).locationCallback = object: LocationCallback(){
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                Log.d("Map","get Map"+p0?.toString())
                p0?.lastLocation.let{
                    (context as MainActivity).latitude = p0.lastLocation.latitude
                    (context as MainActivity).longitude = p0.lastLocation.longitude
                    publishPosts(googleMap)
                }
            }
        }

        (context as MainActivity).subscribeLocationChange()
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

    private fun publishPosts(googleMap: GoogleMap){
        val lat = (context as MainActivity).latitude
        val lon = (context as MainActivity).longitude
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(lat, lon)))
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(10.0F))
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

}