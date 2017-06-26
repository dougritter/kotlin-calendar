package com.ritterdouglas.calendar.ui.custom_view

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.widget.DatePicker
import com.ritterdouglas.calendar.MainView

import java.util.Calendar

class DatePickerFragment(val minDate: Long, var listener: MainView?, val moveType: Int) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    companion object {
        val TAG = DatePickerFragment::class.java.simpleName
        val MOVE_IN: Int = 100
        val MOVE_OUT: Int = 101
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(activity, this, year, month, day)
        datePicker.datePicker.minDate = minDate - 1000
        return datePicker
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        Log.e(tag, "on date set: $day $month $year")
        listener?.onDateSelected(year, month, day, moveType)
    }

    override fun onDestroy() {
        listener = null
        super.onDestroy()
    }
}