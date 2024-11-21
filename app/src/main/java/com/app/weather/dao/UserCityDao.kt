package com.app.weather.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.app.weather.entity.HistoryCity
import com.app.weather.entity.UserCity

@Dao
interface UserCityDao {

    @Insert
    fun insertCity(userCity: UserCity): Long

    @Delete
    fun deleteCity(userCity: UserCity)

    @Query("DELETE FROM user_city WHERE userId = :userId AND cityId = :cityId")
    fun deleteByUserIdAndCityId(userId: Int, cityId: Int): Int

}