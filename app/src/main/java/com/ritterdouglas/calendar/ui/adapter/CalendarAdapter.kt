package com.ritterdouglas.calendar.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.ritterdouglas.calendar.data.Month
import android.view.LayoutInflater
import com.ritterdouglas.calendar.R
import com.ritterdouglas.calendar.ui.view_holder.CalendarViewHolder
import com.ritterdouglas.calendar.ui.view_holder.HeaderDaysViewHolder
import com.ritterdouglas.calendar.ui.view_holder.HeaderTitleViewHolder


/**
 * Created by douglasritter on 22/06/17.
 */

class CalendarAdapter(context: Context, data: List<Month>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mData = prepareLists(data)

    companion object {
        val TAG = CalendarAdapter::class.java.simpleName
        val HEADER = 0
        val CALENDAR = 1
    }

    override fun getItemCount() = mData.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?, position: Int) {
        when (viewHolder?.itemViewType) {
            HEADER -> {
                val vh = viewHolder as HeaderTitleViewHolder
                configureHeaderViewHolder(vh, position)
            }
            else -> {
                val vh = viewHolder as CalendarViewHolder
                configureCalendarViewHolder(vh, position)
            }
        }
    }

    private fun configureHeaderViewHolder(viewHolder: HeaderTitleViewHolder, position: Int) {
        viewHolder.headerTitle?.text = (mData[position].monthName + " "+ mData[position].year).capitalize()
    }

    private fun configureCalendarViewHolder(viewHolder: CalendarViewHolder, position: Int) {
        viewHolder.gridView.isExpanded = true
        viewHolder.gridView.adapter = GridAdapter(mData[position])
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        val inflater = LayoutInflater.from(viewGroup?.context)

        when (viewType) {
            HEADER -> {
                val headerViewHolder = inflater.inflate(R.layout.item_header_title, viewGroup, false)
                viewHolder = HeaderTitleViewHolder(headerViewHolder)
            }
            else -> { //CALENDAR
                val calendarViewHolder = inflater.inflate(R.layout.item_calendar, viewGroup, false)
                viewHolder = CalendarViewHolder(calendarViewHolder)
            }
        }
        return viewHolder
    }

    override fun getItemViewType(position: Int) = if (position % 2 == 0) HEADER else CALENDAR

    fun prepareLists(data: List<Month>): MutableList<Month> {
        val doubleMonths: MutableList<Month> = arrayListOf()
        for (i in 0..data.size-1) {
            doubleMonths.add(data[i])
            doubleMonths.add(data[i])
        }
        return doubleMonths
    }

    fun getMonthForCurrentDay(year: Int, month: Int, day: Int): Int {
        var position = 0
        if (mData.size >= month) {
            for (i in 0..mData.size-1)
                if (mData[i].month == month) position = i
        }
        return position
    }

}
