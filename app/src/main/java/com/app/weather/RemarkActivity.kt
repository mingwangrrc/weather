package com.app.weather

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.app.weather.adapter.RemarkAdapter
import com.app.weather.dao.RemarkDao
import com.app.weather.db.AppDatabase
import com.app.weather.entity.Remark
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RemarkActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var remarkListView: ListView
    lateinit var remarkAdapter: RemarkAdapter
    lateinit var remarkList: MutableList<Remark>
    lateinit var addMsgBt: Button
    lateinit var remarkDao: RemarkDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remark)

        remarkDao = AppDatabase.getInstance(this).remarkDao()

        initView()
    }


    fun initView() {

        toolbar = findViewById(R.id.toolbarstuMin)
        addMsgBt = findViewById(R.id.addMsgBt)

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        remarkList = ArrayList()

        remarkListView = findViewById(R.id.remarkList)
        remarkAdapter = RemarkAdapter(this, remarkList)
        remarkListView.adapter = remarkAdapter


        addMsgBt.setOnClickListener {
            val intent = Intent(this, InputRemarkActivity::class.java)
            startActivity(intent)
        }

        reloadListView()

    }

    override fun onResume() {
        super.onResume()

        reloadListView()
    }

    fun reloadListView() {
        val sharedPreferences = getSharedPreferences("weather", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("name", "")
        lifecycleScope.launch(Dispatchers.IO) {
            try {

                if (userName != null) {
                    remarkList.clear()
                    remarkList.addAll(remarkDao.getRemarkListByUserName(userName))
                    withContext(Dispatchers.Main) {
                        remarkAdapter.notifyDataSetChanged()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }




}