package com.app.weather

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.weather.dao.UserDao
import com.app.weather.db.AppDatabase
import com.app.weather.encryptor.AesEncryptor
import com.app.weather.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    lateinit var nameET : EditText
    lateinit var passwordET : EditText
    lateinit var loginBt : Button
    lateinit var registerBt : TextView
    lateinit var userDao: UserDao
    lateinit var forgotBt: TextView



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userDao = AppDatabase.getInstance(this).userDao()

        initView()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun initView() {
        loginBt = findViewById(R.id.loginBt);
        loginBt.setOnClickListener {
            loginAction()
        }

        registerBt = findViewById(R.id.registerBt)
        registerBt.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        forgotBt = findViewById(R.id.forgetPwdBt)
        forgotBt.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }


        nameET = findViewById(R.id.name_et);
        passwordET = findViewById(R.id.pwd_et);


        val sharedPreferences = getSharedPreferences("weather", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("name", "")
        if (!(userName.isNullOrEmpty())) {
            goMain()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loginAction() {
        val name = nameET.text.toString()
        val pwd = passwordET.text.toString()
        val aesEncryptor = AesEncryptor()
        GlobalScope.launch(Dispatchers.IO) {
            val existUser = userDao.getUserWithName(name,aesEncryptor.encrypt(pwd))
            if (existUser.size != 0) {
                runOnUiThread {
                    var user: User = existUser.get(0)
                    val sharedPreferences = getSharedPreferences("weather", Context.MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString("name", user.name)
                    editor.putString("pwd", user.password)
                    editor.putInt("id", user.id)
                    editor.apply()
                    goMain()
                }
            }else {
                runOnUiThread {
                    Toast.makeText(this@LoginActivity, "The user name and password are incorrect", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun goMain() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }


}