package com.app.weather.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.app.weather.entity.User

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User): Long

    @Update
    fun updateUser(newUser: User): Int

    @Query("select * from User")
    fun loadAllUser(): List<User>

    @Query("select * from User where name = :name and password = :password")
    fun getUserWithName(name: String, password: String): List<User>

    @Query("select * from User where id = :userId")
    fun getUserWithUserId(userId: Int): User

    @Query("select * from User where name = :name")
    fun getUserWithAccount(name: String): List<User>


    @Query("select * from User where name = :name and email = :email")
    fun getUserWithAccountAndEmail(name: String,email: String ): List<User>



    @Delete
    fun deleteUser(user: User)

    @Query("delete from User where name = :name")
    fun deleteUserByUserName(name: String)


}