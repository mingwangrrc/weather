package com.app.weather

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.app.weather.fragment.HomeFragment
import com.app.weather.fragment.MineFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.home_item -> selectedFragment = HomeFragment()
                R.id.mine -> selectedFragment = MineFragment()
            }

            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.content_frame, selectedFragment)
                    .commit()
            }
            true
        }

        // 默认加载首页
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
            HomeFragment()).commit();

    }
}