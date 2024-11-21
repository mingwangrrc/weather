package com.app.weather

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.KeyEventDispatcher.Component
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.weather.adapter.HistoryCityAdapter
import com.app.weather.adapter.RemarkAdapter
import com.app.weather.entity.Remark
import com.app.weather.entity.WeatherInfo
import com.bumptech.glide.Glide
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class WeatherActivity : AppCompatActivity() {

    lateinit var titleCity: TextView
    lateinit var titleUpdateTime: TextView
    lateinit var weatherIcon: ImageView
    lateinit var temperatureText: TextView
    lateinit var weatherDescriptionsText: TextView
    lateinit var countryText: TextView
    lateinit var latLngText: TextView
    lateinit var weatherInfoText: TextView
    lateinit var backBt: ImageButton


    lateinit var wInfo: WeatherInfo
    lateinit var wDesc: String

    lateinit var cityName: String

    lateinit var messageBt: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        cityName = intent.getStringExtra("city").toString()

        initView()

        makeGetRequest()

    }

    fun initView() {
        titleCity = findViewById(R.id.titleCity)

        titleCity.text = cityName

        titleUpdateTime = findViewById(R.id.titleUpdateTime)
        weatherIcon = findViewById(R.id.weatherIcon)
        temperatureText = findViewById(R.id.temperatureText)

        weatherDescriptionsText = findViewById(R.id.weatherDescriptionsText)
        countryText = findViewById(R.id.countryText)
        latLngText = findViewById(R.id.latLngText)
        weatherInfoText = findViewById(R.id.weatherInfoText)

        backBt = findViewById(R.id.backBt)
        backBt.setOnClickListener {
            finish()
        }

        messageBt = findViewById(R.id.messageBt)
        messageBt.setOnClickListener {
            val intent = Intent(this, RemarkActivity::class.java)
            startActivity(intent)

        }


        val sharedPreferences = getSharedPreferences("weather", Context.MODE_PRIVATE)
        val oldTextColor = sharedPreferences.getString("textColor", "#000000")
        val oldTextSize = sharedPreferences.getInt("textSize", 0)

        titleCity.setTextSize(TypedValue.COMPLEX_UNIT_PX,titleCity.textSize+oldTextSize)
        titleCity.setTextColor(Color.parseColor(oldTextColor))

        titleUpdateTime.setTextSize(TypedValue.COMPLEX_UNIT_PX,titleUpdateTime.textSize+oldTextSize)
        titleUpdateTime.setTextColor(Color.parseColor(oldTextColor))

        temperatureText.setTextSize(TypedValue.COMPLEX_UNIT_PX,temperatureText.textSize+oldTextSize)
        temperatureText.setTextColor(Color.parseColor(oldTextColor))

        weatherDescriptionsText.setTextSize(TypedValue.COMPLEX_UNIT_PX,weatherDescriptionsText.textSize+oldTextSize)
        weatherDescriptionsText.setTextColor(Color.parseColor(oldTextColor))

        countryText.setTextSize(TypedValue.COMPLEX_UNIT_PX,countryText.textSize+oldTextSize)
        countryText.setTextColor(Color.parseColor(oldTextColor))

        latLngText.setTextSize(TypedValue.COMPLEX_UNIT_PX,latLngText.textSize+oldTextSize)
        latLngText.setTextColor(Color.parseColor(oldTextColor))


        weatherInfoText.setTextSize(TypedValue.COMPLEX_UNIT_PX,weatherInfoText.textSize+oldTextSize)
        weatherInfoText.setTextColor(Color.parseColor(oldTextColor))

    }




    fun setViewData() {
        titleCity.text = wInfo.city
        titleUpdateTime.text = wInfo.dateTime

        temperatureText.text = wInfo.temperature.toString() + "°C"
        weatherDescriptionsText.text = wInfo.weatherDescriptions
        countryText.text = wInfo.country
        latLngText.text = wInfo.latlng
        weatherInfoText.text = wDesc

        Glide.with(this)
            .load(wInfo.weatherIcon)
            .into(weatherIcon);
    }

    fun makeGetRequest() {
        val url =
            URL("https://api.weatherstack.com/current?access_key=e6a25b22a31b600d51e81a5e59480cee&query=" + cityName)
        Thread {
            try {
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()
                val response =
                    BufferedReader(InputStreamReader(connection.inputStream)).use { it.readText() }
                // 更新 UI 需要在主线程
                runOnUiThread {
                    println(response)
                    val jsonObject = JSONObject(response)
                    if (jsonObject.has("success")) {
                        val success = jsonObject.getBoolean("success")
                        if (!success) {
                            Toast.makeText(this, "Please enter the correct city", Toast.LENGTH_SHORT).show()
                        }
                    }else {
                        wInfo = WeatherInfo()
                        if (jsonObject.has("location")) {
                            val locationObj = jsonObject.getJSONObject("location")
                            wInfo.city = locationObj.getString("name")
                            wInfo.country = locationObj.getString("country")
                            wInfo.latlng = locationObj.getString("lat")+"/"+locationObj.getString("lon")
                            wInfo.dateTime = locationObj.getString("localtime")
                        }

                        if (jsonObject.has("current")) {
                            val currentObj = jsonObject.getJSONObject("current")
                            wInfo.temperature = currentObj.getInt("temperature")
                            wInfo.observationTime = currentObj.getString("observation_time")
                            val jsonArray = currentObj.getJSONArray("weather_icons")
                            if (jsonArray.length() > 0) {
                               wInfo.weatherIcon = jsonArray.get(0).toString()
                            }
                            val weatherDescriptions = currentObj.getJSONArray("weather_descriptions")
                            if (weatherDescriptions.length() > 0) {
                                wInfo.weatherDescriptions = weatherDescriptions.get(0).toString()
                            }
                            wInfo.windSpeed = currentObj.getInt("wind_speed")
                            wInfo.windDegree = currentObj.getInt("wind_degree")
                            wInfo.pressure = currentObj.getInt("pressure")
                            wInfo.humidity = currentObj.getInt("humidity")
                            wInfo.cloudcover = currentObj.getInt("cloudcover")
                            wInfo.feelslike = currentObj.getInt("feelslike")
                            wInfo.uvIndex = currentObj.getInt("uv_index")
                        }
                        wDesc =
                            "The current observation time is " + wInfo.observationTime + ", and the temperature is " + wInfo.temperature + "°C with an " + wInfo.weatherDescriptions + " sky. " +
                                    "The wind is coming from the NNW (" + wInfo.windDegree + "°) at a speed of " + wInfo.windSpeed + " km/h. " +
                                    "The atmospheric pressure is " + wInfo.pressure + " hPa, and there's no precipitation. " +
                                    "The relative humidity is " + wInfo.humidity + "%, and the cloud cover is complete at " + wInfo.cloudcover + "%. " +
                                    "It feels like " + wInfo.feelslike + "°C, and the UV index is at " + wInfo.uvIndex + ", indicating a low level of ultraviolet radiation. "
                        setViewData()

                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()

    }


}