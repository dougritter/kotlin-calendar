package com.ritterdouglas.calendar.ui.presenter

import android.util.Log
import com.google.gson.Gson
import com.ritterdouglas.calendar.MainActivity
import com.ritterdouglas.calendar.MainView
import com.ritterdouglas.calendar.data.CalendarGenerator
import com.ritterdouglas.calendar.data.Day
import com.ritterdouglas.calendar.data.Year
import com.ritterdouglas.calendar.ui.custom_view.DatePickerFragment
import com.ritterdouglas.calendar.ui.custom_view.DatePickerFragment.Companion.MOVE_IN
import java.util.*

/**
 * Created by douglasritter on 23/06/17.
 */

class MainPresenter {

    lateinit var mainView: MainView
    var moveInDate: Long = 0

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

    fun getCurrentTimeStamp(): Long = System.currentTimeMillis()

    fun getMinDateTimpeStamp(): Long = if (moveInDate > 0) moveInDate else getCurrentTimeStamp()

    fun onDateSelected(year: Int, month: Int, day: Int, moveType: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)

        when (moveType) {
            DatePickerFragment.MOVE_IN -> {
                moveInDate = calendar.timeInMillis
                mainView?.selectMoveIn(moveInDate)
                mainView?.allowMoveOut(true)
            }
            DatePickerFragment.MOVE_OUT -> {
                mainView?.selectMoveOut(calendar.timeInMillis)
                mainView?.allowMoveOut(true)
            }

        }

    }


}
