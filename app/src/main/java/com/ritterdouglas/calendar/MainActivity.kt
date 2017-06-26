package com.ritterdouglas.calendar

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import android.widget.Toast
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

        moveInButton?.setOnClickListener({ showDatePickerDialog(it, DatePickerFragment.MOVE_IN) })

    }

    override fun onResume() {
        super.onResume()
        presenter.setCurrentDay()
    }

    fun showDatePickerDialog(v: View, moveType: Int) {
        val newFragment = DatePickerFragment(presenter.getMinDateTimpeStamp(), this, moveType)
        newFragment.show(supportFragmentManager, DatePickerFragment.TAG)
    }

    override fun bindDays(days: List<Pair<String, String>>) {
        day1?.text = days[0].second
        day2?.text = days[1].second
        day3?.text = days[2].second
        day4?.text = days[3].second
        day5?.text = days[4].second
        day6?.text = days[5].second
        day7?.text = days[6].second
    }

    override fun bindDataToAdapter(data: List<Month>) {
        calendarRecyclerView?.adapter = CalendarAdapter(this, data)
    }

    override fun setCurrentDay(year: Int, month: Int, day: Int) {
        val adapter = (calendarRecyclerView?.adapter) as CalendarAdapter
        calendarRecyclerView?.layoutManager?.scrollToPosition(adapter.getMonthForCurrentDay(year, month, day))
    }

    override fun onDateSelected(year: Int, month: Int, day: Int, moveType: Int) {
        presenter.onDateSelected(year, month, day, moveType)
    }

    override fun allowMoveOut(allow: Boolean) {
        if (allow) moveOutButton?.setOnClickListener({ showDatePickerDialog(it, DatePickerFragment.MOVE_OUT)})
        else Toast.makeText(this, getString(R.string.error_moveout_not_allowed), Toast.LENGTH_SHORT).show()
    }

    override fun selectMoveIn(moveInDate: Long) {
        ((calendarRecyclerView?.adapter) as CalendarAdapter).moveInSelected(moveInDate)
    }

    override fun selectMoveOut(moveOutDate: Long) {
        ((calendarRecyclerView?.adapter) as CalendarAdapter).moveOutSelected(presenter.getMinDateTimpeStamp(), moveOutDate)
    }

}

interface MainView {
    fun bindDays(days: List<Pair<String, String>>)
    fun bindDataToAdapter(data: List<Month>)
    fun setCurrentDay(year: Int, month: Int, day: Int)
    fun onDateSelected(year: Int, month: Int, day: Int, moveType: Int)
    fun allowMoveOut(allow: Boolean)
    fun selectMoveIn(moveInDate: Long)
    fun selectMoveOut(moveOutDate: Long)
}
