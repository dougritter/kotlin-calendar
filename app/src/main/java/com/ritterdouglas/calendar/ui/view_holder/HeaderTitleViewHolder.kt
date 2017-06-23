package com.ritterdouglas.calendar.ui.view_holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.ritterdouglas.calendar.R

/**
 * Created by douglasritter on 23/06/17.
 */

class HeaderTitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val headerTitle by lazy { itemView.findViewById(R.id.headerTitle) as TextView? }

}