package sk.example.skweather

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.*
import org.json.JSONObject
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private fun makesWeatherDays(city: String) {

        val qVolley = Volley.newRequestQueue(this)
        val url = "http://api.openweathermap.org/data/2.5/forecast?q=$city&lang=sk&units=metric&appid=acbd781e031d2ee5f734e15ee6641aad"


        val jsonRequest = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
                response ->
            for (i in 1..5) {
                val fragment: WeatherDay = supportFragmentManager.findFragmentByTag("day$i") as WeatherDay
                fragment.setViews(response, i)
            }
        }, Response.ErrorListener {

        })
        qVolley.add(jsonRequest)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        // check if permission is granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            location: Location? -> Log.d("mylog", "Longtitude is: ${location?.longitude} and altitude is: ${location?.altitude}");
        } */

    }
    fun searchCity(view: View) {
        val city = findViewById<EditText>(R.id.locationField).text.toString()
        makesWeatherDays(city)
    }
}
