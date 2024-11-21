package com.app.weather

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.app.weather.dao.RemarkDao
import com.app.weather.dao.UserDao
import com.app.weather.db.AppDatabase
import com.app.weather.entity.Remark
import com.app.weather.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class InputRemarkActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var msgET: EditText
    lateinit var submitBt: Button
    lateinit var remarkDao: RemarkDao

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_remark)

        remarkDao = AppDatabase.getInstance(this).remarkDao()

        initView()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun initView() {
        toolbar = findViewById(R.id.toolbarstuMin)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        msgET = findViewById(R.id.textArea)
        submitBt = findViewById(R.id.submitBt)

        submitBt.setOnClickListener {
            saveRemarkAction()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveRemarkAction() {
        val msg = msgET.text.toString()
        val sharedPreferences = getSharedPreferences("weather", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("name", "")
        val currentDateTime: LocalDateTime = LocalDateTime.now()
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        val remark = Remark(msg,userName,currentDateTime.format(formatter))

        GlobalScope.launch(Dispatchers.IO) {
            val insertRemark = remarkDao.insertRemark(remark)
            if (insertRemark.toInt() != 0) {
                finish()
            }
        }



    }





}