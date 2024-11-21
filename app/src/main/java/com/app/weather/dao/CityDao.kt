package com.app.weather.dao

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import com.app.weather.entity.HistoryCity
import com.app.weather.entity.UserCity

data class HistoryCityWithUser(
    @Embedded val userCity: UserCity,
    @Embedded val historyCity: HistoryCity
)


@Dao
interface CityDao {

    @Insert
    fun insertCity(city: HistoryCity): Long

    @Query("SELECT * FROM history_city")
    fun getCityByUserId(): List<HistoryCity>


    @Query("SELECT DISTINCT * FROM history_city INNER JOIN user_city ON history_city.id = user_city.cityId WHERE user_city.userId = :userId")
    fun getNewCityByUserId(userId: Int): List<HistoryCityWithUser>

    @Query("SELECT * FROM history_city INNER JOIN user_city ON history_city.id = user_city.cityId WHERE user_city.userId = :userId and history_city.cityName = :city" )
    fun getCityByUserIdAndCity(city: String, userId: Int): List<HistoryCityWithUser>

    @Delete
    fun deleteCity(city: HistoryCity)

}