package com.app.weather.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.weather.entity.HistoryCity
import com.app.weather.entity.Remark

@Dao
interface RemarkDao {

    @Insert
    fun insertRemark(remark: Remark): Long

    @Query("SELECT * FROM remark  WHERE userName = :userName")
    fun getRemarkListByUserName(userName: String): List<Remark>

}