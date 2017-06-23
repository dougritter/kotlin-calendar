package com.ritterdouglas.calendar.ui.view_holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.ritterdouglas.calendar.R
import kotlinx.android.synthetic.main.item_header_days.*

/**
 * Created by douglasritter on 22/06/17.
 */

class HeaderDaysViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val day1 by lazy { itemView.findViewById(R.id.weekDay1) as TextView? }
    val day2 by lazy { itemView.findViewById(R.id.weekDay2) as TextView? }



}