package com.app.weather.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remark")
data class Remark(var content:String?,var userName: String?,var createTime:String?) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}