package com.ritterdouglas.calendar.data

/**
 * Created by douglasritter on 25/06/17.
 */

enum class DaySelection { NONE, SINGLE, LEFT, RIGHT }

data class Day(val dayNumber: Int,
               val passedDay: Boolean,
               val selection: DaySelection)

data class Month(val year: Int,
                 val monthName: String,
                 val month: Int,
                 var days: List<Day>,
                 val startsAt: Int)

data class Year(val daysOfWeek: List<Pair<String, String>>,
                val months: List<Month>)