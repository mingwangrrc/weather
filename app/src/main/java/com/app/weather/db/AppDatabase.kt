package com.app.weather.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.weather.dao.CityDao
import com.app.weather.dao.RemarkDao
import com.app.weather.dao.UserCityDao
import com.app.weather.dao.UserDao
import com.app.weather.dao.WeatherInfoDao
import com.app.weather.entity.HistoryCity
import com.app.weather.entity.Remark
import com.app.weather.entity.User
import com.app.weather.entity.UserCity
import com.app.weather.entity.WeatherInfo

//WeatherInfo::class
@Database(version = 1, entities = [User::class,HistoryCity::class,UserCity::class,Remark::class], exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun cityDao(): CityDao
    abstract fun userCityDao(): UserCityDao
    abstract fun remarkDao(): RemarkDao
//    abstract fun weatherInfoDao(): WeatherInfoDao


    companion object {

        private var instance: AppDatabase ?= null

        fun getInstance(context: Context) : AppDatabase {
            return instance ?: synchronized(this){
                Room.databaseBuilder(context, AppDatabase::class.java, "weather")
                    .build().also { instance = it }
            }
        }
    }




}