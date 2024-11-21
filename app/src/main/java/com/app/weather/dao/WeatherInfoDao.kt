package com.app.weather.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.weather.entity.User
import com.app.weather.entity.WeatherInfo

@Dao
interface WeatherInfoDao {

    @Insert
    fun insertWeatherInfo(weatherInfo: WeatherInfo): Long

    @Query("select * from weather_info where city = :city")
    fun getWeatherInfoWithCity(city: String): List<User>


}