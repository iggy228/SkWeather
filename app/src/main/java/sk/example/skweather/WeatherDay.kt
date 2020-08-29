package sk.example.skweather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import org.json.JSONObject

class WeatherDay : Fragment() {

    fun setViews(response: JSONObject, day: Int) {
        val oneDay = response.getJSONArray("list").getJSONObject((day - 1) * 8)

        val dayString = "${oneDay.getString("dt_txt").subSequence(8, 10)}.${oneDay.getString("dt_txt").subSequence(5, 7)}.${oneDay.getString("dt_txt").subSequence(0, 4)}"
        view?.findViewById<TextView>(R.id.day_info)?.text = dayString

        val icon = "wi" + oneDay.getJSONArray("weather").getJSONObject(0).getString("icon")

        val iconId = resources.getIdentifier("@drawable/$icon", null, activity?.baseContext?.packageName)
        val imgRes = ResourcesCompat.getDrawable(resources ,iconId, null)

        view?.findViewById<TextView>(R.id.weather_icon)?.setCompoundDrawablesWithIntrinsicBounds(imgRes, null, null, null)
        view?.findViewById<TextView>(R.id.icon_description)?.text = oneDay.getJSONArray("weather").getJSONObject(0).getString("description")

        val temp = oneDay.getJSONObject("main").getString("temp") + "°C"
        val pressure = oneDay.getJSONObject("main").getString("pressure") + "hPa"
        val humidity = oneDay.getJSONObject("main").getString("humidity") + "%"

        view?.findViewById<TextView>(R.id.temperatureField)?.text = temp
        view?.findViewById<TextView>(R.id.pressureField)?.text = pressure
        view?.findViewById<TextView>(R.id.humidityField)?.text = humidity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather_day, container, false)
    }
    
}