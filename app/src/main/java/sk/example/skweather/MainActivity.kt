package sk.example.skweather

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val APIKEY = "acbd781e031d2ee5f734e15ee6641aad"

    private fun makeWeatherUrl(longitude: Double?, latitude: Double?) {
        val url = "http://api.openweathermap.org/data/2.5/forecast?lat=$latitude&lon=$longitude&lang=sk&units=metric&appid=$APIKEY"
        makesWeatherDays(url)
    }

    private fun makeWeatherUrl(city: String) {
        val url = "http://api.openweathermap.org/data/2.5/forecast?q=$city&lang=sk&units=metric&appid=$APIKEY"
        makesWeatherDays(url)
    }

    private fun makesWeatherDays(url: String) {

        val qVolley = Volley.newRequestQueue(this)

        val jsonRequest = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
                response ->
            for (i in 1..5) {
                val fragment: WeatherDay = supportFragmentManager.findFragmentByTag("day$i") as WeatherDay
                fragment.setViews(response, i)
            }
            findViewById<EditText>(R.id.locationField).text = SpannableStringBuilder(response.getJSONObject("city").getString("name"))
        }, Response.ErrorListener {

        })
        qVolley.add(jsonRequest)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // check permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 1)
        }
        fusedLocationClient.lastLocation.addOnSuccessListener {
            location: Location? ->
            if (location != null) {
                makeWeatherUrl(location.longitude, location.latitude)
            }
        }

    }
    fun searchCity(view: View) {
        val city = findViewById<EditText>(R.id.locationField).text.toString()
        makeWeatherUrl(city)
    }
}
