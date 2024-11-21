package com.app.weather.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(var name:String?,var password:String?,var email: String?,var createTime:String?) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}