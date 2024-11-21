package com.app.weather.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.app.weather.R
import com.app.weather.WeatherActivity
import com.app.weather.adapter.DeleteCityInterface
import com.app.weather.adapter.HistoryCityAdapter
import com.app.weather.dao.CityDao
import com.app.weather.dao.HistoryCityWithUser
import com.app.weather.dao.UserCityDao
import com.app.weather.db.AppDatabase
import com.app.weather.entity.HistoryCity
import com.app.weather.entity.User
import com.app.weather.entity.UserCity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.properties.Delegates

class HomeFragment : Fragment(),DeleteCityInterface {

    lateinit var cityListView: ListView
    lateinit var searchBt: Button
    lateinit var searchET: EditText
    lateinit var cityDao: CityDao
    lateinit var userCityDao: UserCityDao

    lateinit var cityLists: MutableList<HistoryCityWithUser>
    var userId = 0
    lateinit var cityAdapter : HistoryCityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cityDao = AppDatabase.getInstance(requireContext()).cityDao()
        userCityDao = AppDatabase.getInstance(requireContext()).userCityDao()
        val sharedPreferences =
            requireContext().getSharedPreferences("weather", Context.MODE_PRIVATE)
        userId = sharedPreferences.getInt("id", 0)


        cityLists = ArrayList()




        cityListView = view.findViewById(R.id.cityList)
        cityAdapter = HistoryCityAdapter(requireContext(), cityLists, this)
        cityListView.adapter = cityAdapter
        cityListView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(requireActivity(), WeatherActivity::class.java)
            val city = cityLists.get(position)
            intent.putExtra("city",city.historyCity.cityName)
            startActivity(intent)
        }

        searchBt = view.findViewById(R.id.searchBt)
        searchBt.setOnClickListener {

            val cityName = searchET.text.toString()
            if (cityName.isNullOrEmpty()) {
                Toast.makeText(requireActivity(), "Please enter the search city", Toast.LENGTH_SHORT).show()
            }else {
                saveSearchCity()
                val intent = Intent(requireActivity(), WeatherActivity::class.java)
                intent.putExtra("city",cityName)
                startActivity(intent)
            }
        }

        searchET = view.findViewById(R.id.search_et)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveSearchCity() {
        val cityName = searchET.text.toString()

        val currentDateTime: LocalDateTime = LocalDateTime.now()
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        val city = HistoryCity(cityName, "", currentDateTime.format(formatter))
        GlobalScope.launch(Dispatchers.IO) {
            val cityByUserIdAndCity = cityDao.getCityByUserIdAndCity(cityName, userId)
            if (cityByUserIdAndCity.size == 0) {
                val cityId = cityDao.insertCity(city)
                val userCity = UserCity(userId, cityId.toInt())
                userCityDao.insertCity(userCity)
            }
        }

    }

    override fun onResume() {
        super.onResume()

        reloadListView()

        val sharedPreferences = requireActivity().getSharedPreferences("weather", Context.MODE_PRIVATE)
        val oldTextColor = sharedPreferences.getString("textColor", "#000000")

        searchET.setTextColor(Color.parseColor(oldTextColor))


    }


    fun reloadListView() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                cityLists.clear()
                cityLists.addAll(cityDao.getNewCityByUserId(userId))
                withContext(Dispatchers.Main) {
                    cityAdapter.notifyDataSetChanged()
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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun deleteCityWithIndex(position: Int) {
        println(position)

        lifecycleScope.launch(Dispatchers.IO) {
            val city = cityLists[position]
            userCityDao.deleteByUserIdAndCityId(userId,city.historyCity.id)
            cityDao.deleteCity(city.historyCity)

            try {
                cityLists.clear()
                cityLists.addAll(cityDao.getNewCityByUserId(userId))
                withContext(Dispatchers.Main) {
                    cityAdapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

}