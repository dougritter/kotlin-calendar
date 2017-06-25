package com.ritterdouglas.calendar.ui.presenter

import android.util.Log
import com.google.gson.Gson
import com.ritterdouglas.calendar.MainActivity
import com.ritterdouglas.calendar.MainView
import com.ritterdouglas.calendar.data.CalendarGenerator
import com.ritterdouglas.calendar.data.Year
import java.util.*

/**
 * Created by douglasritter on 23/06/17.
 */

class MainPresenter {

    lateinit var mainView: MainView

    fun init() {
        val data = CalendarGenerator.generateGsonCalendar(2017)
        val year = Gson().fromJson(data.toString(), Year::class.java)

        Log.e(MainActivity.TAG, "data from calendar generator: \n ${year.toString()}")
        mainView?.bindDays(year.daysOfWeek)
        CalendarGenerator.addGhostDays(year)
        mainView?.bindDataToAdapter(year.months)
    }

    fun setCurrentDay() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        mainView.setCurrentDay(year, month, day)
    }

}
