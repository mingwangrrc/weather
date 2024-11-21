package com.app.weather.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_city")
data class UserCity(var userId:Int?,var cityId:Int?) {

    @PrimaryKey(autoGenerate = true)
    var uc_id: Int = 0
}