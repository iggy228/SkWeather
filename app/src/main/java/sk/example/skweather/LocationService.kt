package sk.example.skweather

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices

class LocationService : Service() {

    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    lateinit var locationCallback: LocationCallback

    override fun onBind(p0: Intent?): IBinder? {
        return null;
    }

    override fun onCreate() {
        super.onCreate()
        val locationCallback = LocationCallback()
    }
}