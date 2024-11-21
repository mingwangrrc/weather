package com.app.weather.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_city")
data class HistoryCity(var cityName:String?,var contry: String?,var createTime: String?) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}