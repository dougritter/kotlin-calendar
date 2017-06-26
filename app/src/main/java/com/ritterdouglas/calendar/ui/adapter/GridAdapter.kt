package com.ritterdouglas.calendar.ui.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.ritterdouglas.calendar.data.Month
import android.widget.TextView
import com.ritterdouglas.calendar.R
import com.ritterdouglas.calendar.data.DaySelection


/**
 * Created by douglasritter on 22/06/17.
 */

class GridAdapter(data: Month) : BaseAdapter() {

    companion object { val TAG = GridAdapter::class.java.simpleName }

    val mData = data

    override fun isEnabled(position: Int) = false

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val textView: TextView

        val dayView: View

        if (convertView == null) {
            val inflater = LayoutInflater.from(parent?.context)
            dayView = inflater.inflate(R.layout.item_day, null)

        } else dayView = convertView

        val dayNumber = dayView.findViewById(R.id.dayNumber) as TextView
        val singleSelection = dayView.findViewById(R.id.singleSelection)
        val leftSelection = dayView.findViewById(R.id.leftSelection)
        val rightSelection = dayView.findViewById(R.id.rightSelection)

        val day = mData.days[position]

        if (day.dayNumber > 0) {
            dayNumber.text = day.dayNumber.toString()
            if (day.passedDay) dayNumber.alpha = 0.4f

            if (day.selection != DaySelection.NONE) dayNumber.setTextColor(ContextCompat.getColor(dayView.context, android.R.color.black))

            when (day.selection) {
                DaySelection.SINGLE -> { singleSelection.visibility = View.VISIBLE }
                DaySelection.LEFT -> {
                    singleSelection.visibility = View.VISIBLE
                    leftSelection.visibility = View.VISIBLE
                }
                DaySelection.RIGHT -> {
                    singleSelection.visibility = View.VISIBLE
                    rightSelection.visibility = View.VISIBLE
                }
                DaySelection.FULL -> {
                    leftSelection.visibility = View.VISIBLE
                    rightSelection.visibility = View.VISIBLE
                    singleSelection.visibility = View.VISIBLE
                }
            }
        } else {
            dayNumber.text = ""
        }


        return dayView
    }

    override fun getItem(position: Int) = mData.days[position]

    override fun getItemId(position: Int) = 0L

    override fun getCount() = mData.days.size

}