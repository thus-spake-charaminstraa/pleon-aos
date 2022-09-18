package com.charaminstra.pleon.calendar

import android.view.View
import com.charaminstra.pleon.garden.databinding.CalendarHeaderBinding
import com.kizitonwose.calendarview.ui.ViewContainer

class MonthViewContainer(view: View) : ViewContainer(view) {
    val legendLayout = CalendarHeaderBinding.bind(view).legendLayout.root
}