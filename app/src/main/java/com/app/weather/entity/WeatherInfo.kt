package com.app.weather.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_info")
data class WeatherInfo(var userId:Int = 0,
                       var city:String = "",
                       var country:String? = "",
                       var latlng:String? = "",
                       var dateTime: String? = "",
                       var observationTime: String? = "",
                       var weatherDescriptions: String? = "",
                       var windDegree: Int? = 0,
                       var pressure: Int? = 0,
                       var temperature: Int? = 0,
                       var weatherIcon: String? = "",
                       var windSpeed: Int? = 0,
                       var cloudcover: Int? = 0,
                       var feelslike: Int? = 0,
                       var uvIndex: Int? = 0,
                       var humidity: Int? = 0) {

    init {

    }

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}