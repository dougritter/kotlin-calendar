package com.ritterdouglas.calendar.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.ritterdouglas.calendar.data.Month
import android.view.LayoutInflater
import com.ritterdouglas.calendar.R
import com.ritterdouglas.calendar.data.DaySelection
import com.ritterdouglas.calendar.ui.view_holder.CalendarViewHolder
import com.ritterdouglas.calendar.ui.view_holder.HeaderTitleViewHolder
import java.util.*


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
                if (mData[i].month == month) {
                    position = i
                    break
                }
        }
        return position
    }

    fun moveInSelected(moveInDate: Long) {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = moveInDate

        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val monthIndex = (0..mData.size-1).lastOrNull { mData[it].month == month } ?: 0

        var actualDayNumber = mData[monthIndex].startsAt
        if (actualDayNumber >= 1) actualDayNumber -= 2
        mData[monthIndex].days[day + actualDayNumber].selection = DaySelection.SINGLE

        notifyDataSetChanged()

    }

    fun moveOutSelected(moveInDate: Long, moveOutDate: Long) {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = moveInDate

        val monthIn = calendar.get(Calendar.MONTH)
        val dayIn = calendar.get(Calendar.DAY_OF_MONTH)

        calendar.timeInMillis = moveOutDate
        val monthOut = calendar.get(Calendar.MONTH)
        val dayOut = calendar.get(Calendar.DAY_OF_MONTH)

        for (i in 0..mData.size-1) {
            if (mData[i].month in monthIn..monthOut) {

                val currentMonth = mData[i]
                for ((dayNumber) in currentMonth.days) {
                    var actualDayNumber = currentMonth.startsAt
                    if (actualDayNumber >= 1) actualDayNumber -= 2

                    if (currentMonth.month == monthIn && currentMonth.month == monthOut) {
                        if (dayNumber == dayIn) currentMonth.days[dayNumber + actualDayNumber].selection = DaySelection.RIGHT
                        else if (dayNumber == dayOut) currentMonth.days[dayNumber + actualDayNumber].selection = DaySelection.LEFT
                        else if (dayNumber > dayIn && dayNumber < dayOut) currentMonth.days[dayNumber + actualDayNumber].selection = DaySelection.FULL
                    }

                    else if (currentMonth.month == monthIn) {
                        if (dayNumber == dayIn) currentMonth.days[dayNumber + actualDayNumber].selection = DaySelection.RIGHT
                        else if (dayNumber > dayIn) currentMonth.days[dayNumber + actualDayNumber].selection = DaySelection.FULL
                    }

                    else if (currentMonth.month == monthOut) {
                        if (dayNumber == dayOut) currentMonth.days[dayNumber + actualDayNumber].selection = DaySelection.LEFT
                        else if (dayNumber < dayOut) currentMonth.days[dayNumber + actualDayNumber].selection = DaySelection.FULL
                    }

                    else currentMonth.days[dayNumber + actualDayNumber].selection = DaySelection.FULL

                }

            }
        }


//        val monthIndex = (0..mData.size-1).lastOrNull { mData[it].month == month } ?: 0



        /*var actualDayNumber = mData[monthIndex].startsAt
        if (actualDayNumber >= 1) actualDayNumber -= 2
        mData[monthIndex].days[day + actualDayNumber].selection = DaySelection.SINGLE*/

        notifyDataSetChanged()

    }

}
