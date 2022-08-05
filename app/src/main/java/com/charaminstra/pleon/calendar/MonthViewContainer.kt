package com.charaminstra.pleon.calendar

import android.view.View
import android.widget.TextView
import com.charaminstra.pleon.databinding.CalendarDayLayoutBinding
import com.charaminstra.pleon.databinding.CalendarHeaderBinding
import com.kizitonwose.calendarview.ui.ViewContainer

class MonthViewContainer(view: View) : ViewContainer(view) {
    val legendLayout = CalendarHeaderBinding.bind(view).legendLayout.root
}