package com.app.weather

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

class EditAccountActivity : AppCompatActivity() {

    lateinit var emailET: EditText
    lateinit var passwordET: EditText
    lateinit var accountET: EditText
    lateinit var saveBt: Button
    lateinit var toolbar: Toolbar
    lateinit var userDao: UserDao
    lateinit var user: User


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_account)

        userDao = AppDatabase.getInstance(this).userDao()

        initView()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initView() {

        emailET = findViewById(R.id.email_et)
        accountET = findViewById(R.id.username_et)
        passwordET = findViewById(R.id.password_et)
        toolbar = findViewById(R.id.toolbarstuMin)
        saveBt = findViewById(R.id.saveBt)

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        saveBt.setOnClickListener {
            saveAction()
        }


        val sharedPreferences = getSharedPreferences("weather", Context.MODE_PRIVATE)
        val id = sharedPreferences.getInt("id", 0)
        val aesEncryptor = AesEncryptor()
        lifecycleScope.launch(Dispatchers.IO) {
            val userWithUserId = userDao.getUserWithUserId(id)
            user = userWithUserId
            val dePwd = userWithUserId.password?.let { aesEncryptor.decrypt(it) }
            val deEmail = userWithUserId.email?.let { aesEncryptor.decrypt(it) }
            accountET.setText(userWithUserId.name)
            passwordET.setText(dePwd)
            emailET.setText(deEmail)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveAction() {
        val aesEncryptor = AesEncryptor()
        val email = emailET.text.toString();
        val name = accountET.text.toString()
        val pwd = passwordET.text.toString()
        user.email = aesEncryptor.encrypt(email)
        user.password = aesEncryptor.encrypt(pwd)
        user.name = name

        GlobalScope.launch(Dispatchers.IO) {
            val update = userDao.updateUser(user)
            if (update != 0) {
                runOnUiThread {
                    Toast.makeText(this@EditAccountActivity, "Update successful", Toast.LENGTH_SHORT).show()
                }
                finish()
            }

        }

    }



}