package com.charaminstra.pleon.calendar

import android.view.View
import android.widget.TextView
import com.charaminstra.pleon.databinding.CalendarDayLayoutBinding
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.ViewContainer

class DayViewContainer(view: View) : ViewContainer(view) {
    //val textView = view.findViewById<TextView>(R.id.calendarDayText)

    // With ViewBinding
     val textView = CalendarDayLayoutBinding.bind(view).calendarDayText
    val dot1 = CalendarDayLayoutBinding.bind(view).dot1
    val dot2 = CalendarDayLayoutBinding.bind(view).dot2
    val dot3 = CalendarDayLayoutBinding.bind(view).dot3
    val dot4 = CalendarDayLayoutBinding.bind(view).dot4
    val dot5 = CalendarDayLayoutBinding.bind(view).dot5
    val dot6 = CalendarDayLayoutBinding.bind(view).dot6
    lateinit var day: CalendarDay
}