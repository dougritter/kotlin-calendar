package com.ritterdouglas.calendar

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.ritterdouglas.calendar.data.Month
import com.ritterdouglas.calendar.ui.adapter.CalendarAdapter
import com.ritterdouglas.calendar.ui.presenter.MainPresenter
import com.ritterdouglas.calendar.ui.custom_view.DatePickerFragment



class MainActivity : AppCompatActivity(), MainView {

    companion object { val TAG = MainActivity::class.java.simpleName }

    val day1 by lazy { findViewById(R.id.weekDay1) as TextView? }
    val day2 by lazy { findViewById(R.id.weekDay2) as TextView? }
    val day3 by lazy { findViewById(R.id.weekDay3) as TextView? }
    val day4 by lazy { findViewById(R.id.weekDay4) as TextView? }
    val day5 by lazy { findViewById(R.id.weekDay5) as TextView? }
    val day6 by lazy { findViewById(R.id.weekDay6) as TextView? }
    val day7 by lazy { findViewById(R.id.weekDay7) as TextView? }

    val moveInButton by lazy { findViewById(R.id.moveInButton) as AppCompatButton? }
    val moveOutButton by lazy { findViewById(R.id.moveOutButton) as AppCompatButton? }

    val calendarRecyclerView by lazy { findViewById(R.id.calendarRecyclerView) as RecyclerView? }

    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calendarRecyclerView?.layoutManager = LinearLayoutManager(this)

        presenter = MainPresenter()
        presenter.mainView = this
        presenter.init()

        moveInButton?.setOnClickListener(this::showDatePickerDialog)
        moveOutButton?.setOnClickListener(this::showDatePickerDialog)

    }

    override fun onResume() {
        super.onResume()
        presenter.setCurrentDay()
    }

    fun showDatePickerDialog(v: View) {
        val newFragment = DatePickerFragment()
        newFragment.show(supportFragmentManager, "datePicker")
    }

    override fun bindDays(days: List<Pair<String, String>>) {
        day1?.text = days.get(0).second
        day2?.text = days.get(1).second
        day3?.text = days.get(2).second
        day4?.text = days.get(3).second
        day5?.text = days.get(4).second
        day6?.text = days.get(5).second
        day7?.text = days.get(6).second
    }

    override fun bindDataToAdapter(data: List<Month>) {
        calendarRecyclerView?.adapter = CalendarAdapter(this, data)
    }

    override fun setCurrentDay(year: Int, month: Int, day: Int) {
        val adapter = (calendarRecyclerView?.adapter) as CalendarAdapter
        calendarRecyclerView?.layoutManager?.scrollToPosition(adapter.getMonthForCurrentDay(year, month, day))
    }

}

interface MainView {
    fun bindDays(days: List<Pair<String, String>>)
    fun bindDataToAdapter(data: List<Month>)
    fun setCurrentDay(year: Int, month: Int, day: Int)
}
