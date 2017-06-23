package com.ritterdouglas.calendar.ui.view_holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.GridView
import com.ritterdouglas.calendar.R
import com.ritterdouglas.calendar.ui.custom_view.ExpandableHeightGridView
import kotlinx.android.synthetic.main.item_calendar.*

/**
 * Created by douglasritter on 23/06/17.
 */

class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val gridView by lazy { itemView.findViewById(R.id.calendarGridView) as ExpandableHeightGridView }
}