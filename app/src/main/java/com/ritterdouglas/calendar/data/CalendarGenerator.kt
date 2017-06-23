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

    val YEAR = "year"
    val DAYS_OF_WEEK = "days_of_week"
    val DAY_FORMAT = "EEE"
    val MONTH_NAME = "month_name"
    val MONTHS = "months"
    val DAYS = "days"
    val STARTS_AT = "starts_at"
    val MONTH = "month"

    @SuppressLint("SimpleDateFormat")
    fun generateCalendar(year: Int): JSONObject? {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        val monthsJson = JSONArray()

        for (currentMonth in Calendar.JANUARY..Calendar.DECEMBER) {
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            calendar.set(Calendar.MONTH, currentMonth)

            val numDays = calendar.getActualMaximum(Calendar.DATE)
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

            val days = JSONArray()
            for (i in 1..numDays) days.put(i)

            val currentMonthJson = JSONObject()
                    .put(YEAR, year)
                    .put(MONTH_NAME, calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()))
                    .put(MONTH, calendar.get(Calendar.MONTH))
                    .put(DAYS, days)
                    .put(STARTS_AT, dayOfWeek)

            monthsJson.put(currentMonthJson)

        }
        val daysOfWeek = JSONArray()
        for (day in Calendar.SUNDAY..Calendar.SATURDAY) {
            calendar.set(Calendar.DAY_OF_WEEK, day)
            daysOfWeek.put(JSONObject().put(day.toString(), SimpleDateFormat(DAY_FORMAT).format(calendar.time)))
        }

        val yearJson = JSONObject()
                .put(DAYS_OF_WEEK, daysOfWeek)
                .put(MONTHS, monthsJson)

        Log.e(TAG, "Calendar result: $yearJson")

        return yearJson

    }

    @SuppressLint("SimpleDateFormat")
    fun generateGsonCalendar(year: Int): JSONObject? {

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)

        val monthsList: MutableList<Month> = arrayListOf()
        for (currentMonth in Calendar.JANUARY..Calendar.DECEMBER) {
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            calendar.set(Calendar.MONTH, currentMonth)

            val numDays = calendar.getActualMaximum(Calendar.DATE)
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

            val days: MutableList<Int> = (1..numDays).toMutableList()

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
            val auxDays: MutableList<Int> = arrayListOf()
            if (data.months[currentMonth].startsAt > 1) {
                for (ghost in 0..data.months[currentMonth].startsAt-2) auxDays.add(-1)
                (0..data.months[currentMonth].days.size-1).mapTo(auxDays) { data.months[currentMonth].days[it] }

                data.months[currentMonth].days = auxDays
            }

        }

    }

}


data class Month(val year: Int,
                 val monthName: String,
                 val month: Int,
                 var days: List<Int>,
                 val startsAt: Int)

data class Year(val daysOfWeek: List<Pair<String, String>>,
                val months: List<Month>)