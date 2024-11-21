package com.app.weather.adapter

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.app.weather.R
import com.app.weather.dao.HistoryCityWithUser
import com.app.weather.entity.HistoryCity

class HistoryCityAdapter(private val context: Context, private val citys: List<HistoryCityWithUser>, private val deleteCityInterface: DeleteCityInterface) : BaseAdapter(){

    override fun getCount(): Int = citys.size

    override fun getItem(position: Int): Any = citys[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val city = citys[position]
        viewHolder.textViewName.text = city.historyCity.cityName

        viewHolder.deleteImg.setOnClickListener {
            deleteCityInterface.deleteCityWithIndex(position)
        }

        val sharedPreferences = context.getSharedPreferences("weather", Context.MODE_PRIVATE)
        val oldTextColor = sharedPreferences.getString("textColor", "#000000")
        val oldTextSize = sharedPreferences.getInt("textSize", 0)

        viewHolder.textViewName.setTextSize(TypedValue.COMPLEX_UNIT_PX,viewHolder.textViewName.textSize+oldTextSize)
        viewHolder.textViewName.setTextColor(Color.parseColor(oldTextColor))



        return view
    }

    private class ViewHolder(view: View) {
        val textViewName: TextView = view.findViewById(R.id.cityName)
        val deleteImg: ImageView = view.findViewById(R.id.delete_img)
    }



}

interface DeleteCityInterface {
    fun deleteCityWithIndex(position: Int)
}