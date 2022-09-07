package com.charaminstra.pleon.common_ui

import android.app.Activity
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast

fun Toast.showErrorToast(message: String,relativeLocation: Float, activity: Activity)
{
    val layout = activity.layoutInflater.inflate (
        R.layout.custom_toast_error_layout,
        activity.findViewById(R.id.toast_container)
    )

    // set the text of the TextView of the message
    val textView = layout.findViewById<TextView>(R.id.toast_text)
    textView.text = message

    // use the application extension function
    this.apply {
        setGravity(Gravity.TOP, 0, relativeLocation.toInt()+150)
        duration = Toast.LENGTH_LONG
        view = layout
        show()
    }
}