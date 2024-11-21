package com.app.weather.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.*
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.app.weather.EditAccountActivity
import com.app.weather.MainActivity
import com.app.weather.R
import com.app.weather.dao.UserDao
import com.app.weather.db.AppDatabase
import com.app.weather.encryptor.AesEncryptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MineFragment : Fragment() {

    lateinit var userDao: UserDao
    lateinit var nameTxt: TextView
    lateinit var emaiTxt: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userDao = AppDatabase.getInstance(requireContext()).userDao()


        val logoutBt:Button = view.findViewById(R.id.logoutBt)
        logoutBt.setOnClickListener {
            logoutAction()
        }

        val sharedPreferences = requireActivity().getSharedPreferences("weather", Context.MODE_PRIVATE)

        val id = sharedPreferences.getInt("id", 0)
        nameTxt = view.findViewById(R.id.accountTx)
        emaiTxt = view.findViewById(R.id.emailTx)

        val aesEncryptor = AesEncryptor()

        lifecycleScope.launch(Dispatchers.IO) {
            val userWithUserId = userDao.getUserWithUserId(id)
            val deEmail = userWithUserId.email?.let { aesEncryptor.decrypt(it) }
            nameTxt.text = "Account: "+ userWithUserId.name
            emaiTxt.text = "Email: " + deEmail
        }

        val editBt: Button = view.findViewById(R.id.editBt)
        editBt.setOnClickListener {
            val intent = Intent(requireActivity(), EditAccountActivity::class.java)
            startActivity(intent)
        }


        val addBt:TextView = view.findViewById(R.id.addBt)
        val reduceBt:TextView = view.findViewById(R.id.reduceBt)
        val sizeET: EditText = view.findViewById(R.id.sizeET)
        val colorBt:Button = view.findViewById(R.id.colorBt)

        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        val oldTextSize = sharedPreferences.getInt("textSize", 0)
        sizeET.setText(oldTextSize.toString())

        addBt.setOnClickListener {
            var size = sizeET.text.toString().toInt()
            size++
            sizeET.setText(size.toString())
            editor.putInt("textSize",size)
            editor.apply()

            nameTxt.setTextSize(TypedValue.COMPLEX_UNIT_PX,nameTxt.textSize+size)
            emaiTxt.setTextSize(TypedValue.COMPLEX_UNIT_PX,emaiTxt.textSize+size)
        }

        reduceBt.setOnClickListener {
            var size = sizeET.text.toString().toInt()
            size--
            sizeET.setText(size.toString())
            editor.putInt("textSize",size)
            editor.apply()

            nameTxt.setTextSize(TypedValue.COMPLEX_UNIT_PX,nameTxt.textSize-size)
            emaiTxt.setTextSize(TypedValue.COMPLEX_UNIT_PX,emaiTxt.textSize-size)
        }

        colorBt.setOnClickListener {
            val oldTextColor = sharedPreferences.getString("textColor", "#000000")
            var currentTextColor = "#000000"
            if(oldTextColor.equals("#000000")) {
                currentTextColor = "#FF00FF"
            }else {
                currentTextColor = "#000000"
            }
            editor.putString("textColor",currentTextColor)
            editor.apply()

            nameTxt.setTextColor(Color.parseColor(currentTextColor))
            emaiTxt.setTextColor(Color.parseColor(currentTextColor))
        }


    }

    fun logoutAction() {
        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                val sharedPreferences = requireActivity().getSharedPreferences("weather", Context.MODE_PRIVATE)
                val userName = sharedPreferences.getString("name", "")
                val editor: Editor = sharedPreferences.edit()
                editor.remove("name")
                editor.remove("pwd")
                editor.remove("id")
                editor.apply()

                requireActivity().finish()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()

        val sharedPreferences = requireActivity().getSharedPreferences("weather", Context.MODE_PRIVATE)
        val id = sharedPreferences.getInt("id", 0)
        val oldTextColor = sharedPreferences.getString("textColor", "#000000")
        val oldTextSize = sharedPreferences.getInt("textSize", 0)

        nameTxt.setTextSize(TypedValue.COMPLEX_UNIT_PX,nameTxt.textSize+oldTextSize)
        emaiTxt.setTextSize(TypedValue.COMPLEX_UNIT_PX,emaiTxt.textSize+oldTextSize)
        nameTxt.setTextColor(Color.parseColor(oldTextColor))
        emaiTxt.setTextColor(Color.parseColor(oldTextColor))


        lifecycleScope.launch(Dispatchers.IO) {
            val userWithUserId = userDao.getUserWithUserId(id)
            val aesEncryptor = AesEncryptor()
            val deEmail = userWithUserId.email?.let { aesEncryptor.decrypt(it) }
            try {
                withContext(Dispatchers.Main) {

                    nameTxt.text = "Account: "+ userWithUserId.name
                    emaiTxt.text = "Email: " + deEmail
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }
}