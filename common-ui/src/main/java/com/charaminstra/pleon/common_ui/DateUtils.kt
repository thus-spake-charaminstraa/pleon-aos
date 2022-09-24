package com.charaminstra.pleon.common_ui

import android.content.Context
import android.content.res.Resources
import java.text.SimpleDateFormat
import java.util.*

class DateUtils(context: Context) {
    private lateinit var dateFormat: SimpleDateFormat
    var resources: Resources = context.resources
    fun datePickerToView(date: String):String{
        /* 2022. 9. 13. -> 2022.09.13 */
        dateFormat = SimpleDateFormat(resources.getString(R.string.date_view_format), Locale.ENGLISH)
        return dateFormat.format(dateFormat.parse(date)!!)
    }
    fun viewToSendServer(date: String):String{
        /*  2022.09.13 -> 2022-09-13 */
        return date.replace(".","-")
    }
    fun todayToView(date : Date):String{
        /* -> 2022.09.13 */
        dateFormat = SimpleDateFormat(resources.getString(R.string.date_view_format), Locale.ENGLISH)
        return dateFormat.format(date)
    }
}