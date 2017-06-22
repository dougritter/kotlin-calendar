package com.ritterdouglas.calendar

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import org.json.JSONArray
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = MainActivity::class.java.simpleName
        val DAYS_OF_WEEK = "days_of_week"
        val DAY_FORMAT = "EEE"
        val MONTH_NAME = "month_name"
        val MONTHS = "months"
        val DAYS = "days"
        val STARTS_AT = "starts_at"
        val MONTH = "month"
    }

    val calendarView by lazy { findViewById(R.id.calendarView) as MaterialCalendarView? }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        generateCalendar(2017)

    }

    @SuppressLint("SimpleDateFormat")
    fun generateCalendar(year: Int) {
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

    }


}
