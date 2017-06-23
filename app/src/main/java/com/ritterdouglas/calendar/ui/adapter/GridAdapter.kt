package com.ritterdouglas.calendar.ui.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.ritterdouglas.calendar.data.Month
import android.widget.ImageView.ScaleType
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView


/**
 * Created by douglasritter on 22/06/17.
 */

class GridAdapter(data: Month) : BaseAdapter() {

    companion object { val TAG = GridAdapter::class.java.simpleName }

    val mData = data
//    val mContext = context

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val textView: TextView
        if (convertView == null) {
            textView = TextView(parent?.context)
            textView.setTextColor(ContextCompat.getColor(parent?.context, android.R.color.white))
            textView.layoutParams = ViewGroup.LayoutParams(85, 85)
            textView.setPadding(8, 8, 8, 8)
        } else {
            textView = convertView as TextView
        }

        textView.text = mData.days.get(position).toString()
        return textView
    }

    override fun getItem(position: Int) = mData.days[position]

    override fun getItemId(position: Int) = 0L

    override fun getCount() = mData.days.size

}