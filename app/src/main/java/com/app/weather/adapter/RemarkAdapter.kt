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
import com.app.weather.entity.Remark

class RemarkAdapter(private val context: Context, private val remarks: List<Remark>) : BaseAdapter(){

    override fun getCount(): Int = remarks.size

    override fun getItem(position: Int): Any = remarks[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.remark_item, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val remark = remarks[position]
        viewHolder.remarkTime.text = remark.createTime
        viewHolder.remarkContent.text = remark.content
        viewHolder.userName.text = remark.userName

        val sharedPreferences = context.getSharedPreferences("weather", Context.MODE_PRIVATE)
        val oldTextColor = sharedPreferences.getString("textColor", "#000000")
        val oldTextSize = sharedPreferences.getInt("textSize", 0)

        viewHolder.remarkTime.setTextSize(TypedValue.COMPLEX_UNIT_PX,viewHolder.remarkTime.textSize+oldTextSize)
        viewHolder.remarkTime.setTextColor(Color.parseColor(oldTextColor))

        viewHolder.remarkContent.setTextSize(TypedValue.COMPLEX_UNIT_PX,viewHolder.remarkContent.textSize+oldTextSize)
        viewHolder.remarkContent.setTextColor(Color.parseColor(oldTextColor))

        viewHolder.userName.setTextSize(TypedValue.COMPLEX_UNIT_PX,viewHolder.userName.textSize+oldTextSize)
        viewHolder.userName.setTextColor(Color.parseColor(oldTextColor))


        return view
    }

    private class ViewHolder(view: View) {
        val remarkTime: TextView = view.findViewById(R.id.remark_time)
        val remarkContent: TextView = view.findViewById(R.id.remark_content)
        val userName: TextView = view.findViewById(R.id.user_name)
    }



}
