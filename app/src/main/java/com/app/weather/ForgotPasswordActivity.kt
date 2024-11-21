package com.app.weather

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.app.weather.dao.UserDao
import com.app.weather.db.AppDatabase
import com.app.weather.encryptor.AesEncryptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var emailET: EditText
    lateinit var accountET: EditText
    lateinit var newPwdET: EditText
    lateinit var confirmPwdET: EditText
    lateinit var confirmBt: Button
    lateinit var cancelBt: Button
    lateinit var userDao: UserDao

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        userDao = AppDatabase.getInstance(this).userDao()
        initView()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initView() {
        emailET = findViewById(R.id.email_et)
        accountET = findViewById(R.id.name_et)
        newPwdET = findViewById(R.id.pwd_et)
        confirmPwdET = findViewById(R.id.confirm_pwd_et)
        confirmBt = findViewById(R.id.confirmBt)
        cancelBt = findViewById(R.id.cancleBt)

        confirmBt.setOnClickListener {
            updateAccountPwd()
        }

        cancelBt.setOnClickListener {
            finish()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateAccountPwd() {
        val email = emailET.text.toString()
        val account = accountET.text.toString()
        val newPwd = newPwdET.text.toString()
        val confirmPwd = confirmPwdET.text.toString()
        if (email.isEmpty() || confirmPwd.isEmpty() || account.isEmpty() || newPwd.isEmpty()) {
            Toast.makeText(this@ForgotPasswordActivity, "Please enter complete information", Toast.LENGTH_SHORT).show()
            return
        }
        if (!confirmPwd.equals(newPwd)) {
            Toast.makeText(this@ForgotPasswordActivity, "The two passwords are inconsistent", Toast.LENGTH_SHORT).show()
            return
        }
        val aesEncryptor = AesEncryptor()

        GlobalScope.launch(Dispatchers.IO) {
            val existUser = userDao.getUserWithAccountAndEmail(account,aesEncryptor.encrypt(email))
            if (existUser != null && existUser.isNotEmpty()) {
                val updateUser =  existUser[0]

                updateUser.password = aesEncryptor.encrypt(newPwd)
                val update = userDao.updateUser(updateUser)
                if (update != 0) {
                    runOnUiThread {
                        Toast.makeText(this@ForgotPasswordActivity, "Update successful", Toast.LENGTH_SHORT).show()
                    }
                    finish()
                }
            }else {
                runOnUiThread {
                    Toast.makeText(this@ForgotPasswordActivity, "This user does not exist！", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}