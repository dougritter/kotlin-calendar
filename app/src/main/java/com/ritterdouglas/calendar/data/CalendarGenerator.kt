package com.ritterdouglas.calendar.data

import android.annotation.SuppressLint
import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by douglasritter on 22/06/17.
 */

object CalendarGenerator {

    val TAG = CalendarGenerator::class.java.simpleName

    val DAY_FORMAT = "EEE"

    @SuppressLint("SimpleDateFormat")
    fun generateGsonCalendar(year: Int): JSONObject? {

        val calendar = Calendar.getInstance()

        val currYear = calendar.get(Calendar.YEAR)
        val currMonth = calendar.get(Calendar.MONTH)
        val currDay = calendar.get(Calendar.DAY_OF_MONTH)

        calendar.set(Calendar.YEAR, year)

        val monthsList: MutableList<Month> = arrayListOf()
        for (currentMonth in Calendar.JANUARY..Calendar.DECEMBER) {
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            calendar.set(Calendar.MONTH, currentMonth)

            val numDays = calendar.getActualMaximum(Calendar.DATE)
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

            val days: MutableList<Day> = arrayListOf()
            for (item in 1..numDays) {
                var passedDay = false
                if (year < currYear
                        || (year == currYear && currentMonth < currMonth)
                        || (currentMonth == currMonth && item < currDay))
                    passedDay = true

                days.add(Day(item, passedDay, DaySelection.NONE))

            }

            monthsList.add(Month(
                    year,
                    calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()),
                    calendar.get(Calendar.MONTH),
                    days,
                    dayOfWeek))

        }

        val daysOfWeek: MutableList<Pair<String, String>> = arrayListOf()

        for (day in Calendar.SUNDAY..Calendar.SATURDAY) {
            calendar.set(Calendar.DAY_OF_WEEK, day)
            daysOfWeek.add(Pair(day.toString(), SimpleDateFormat(CalendarGenerator.DAY_FORMAT).format(calendar.time)))
        }

        val result = Year(daysOfWeek, monthsList)
        val jsonResult = JSONObject(Gson().toJson(result))
        Log.e(CalendarGenerator.TAG, "Calendar result: ${jsonResult.toString()}")

        return jsonResult

    }

    fun addGhostDays(data: Year) {
        for (currentMonth in 0..data.months.size-1) {
            val auxDays: MutableList<Day> = arrayListOf()
            if (data.months[currentMonth].startsAt > 1) {
                for (ghost in 0..data.months[currentMonth].startsAt-2) auxDays.add(Day(-1, true, DaySelection.NONE))
                (0..data.months[currentMonth].days.size-1).mapTo(auxDays) { data.months[currentMonth].days[it] }

                data.months[currentMonth].days = auxDays
            }

        }

    }

}