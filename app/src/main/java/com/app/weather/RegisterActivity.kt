package com.app.weather

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.app.weather.dao.UserDao
import com.app.weather.db.AppDatabase
import com.app.weather.encryptor.AesEncryptor
import com.app.weather.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RegisterActivity : AppCompatActivity() {

    lateinit var emailET : EditText
    lateinit var nameET : EditText
    lateinit var pwdET: EditText
    lateinit var signinBt: Button
    lateinit var toolbar: Toolbar
    lateinit var userDao: UserDao



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userDao = AppDatabase.getInstance(this).userDao()

        initView()

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun initView() {
        emailET = findViewById(R.id.email_et)
        nameET = findViewById(R.id.username_et)
        pwdET = findViewById(R.id.password_et)
        signinBt = findViewById(R.id.register_button)
        toolbar = findViewById(R.id.toolbarstuMin)

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }


        signinBt.setOnClickListener {
            signUpAction()
        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun signUpAction() {
        val email = emailET.text.toString();
        val name = nameET.text.toString()
        val pwd = pwdET.text.toString()

        val aesEncryptor = AesEncryptor()


        val currentDateTime: LocalDateTime = LocalDateTime.now()
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        val user = User(name, aesEncryptor.encrypt(pwd), aesEncryptor.encrypt(email), currentDateTime.format(formatter))
        GlobalScope.launch(Dispatchers.IO) {
            val existUser = userDao.getUserWithName(name, pwd)
            if (existUser != null) {
                val insertUser = userDao.insertUser(user)
                if (insertUser.toInt() != 0) {
                    finish()
                }
            }else {
                runOnUiThread {
                    Toast.makeText(this@RegisterActivity, "This user name has already been registered. Please use another user name", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }



}