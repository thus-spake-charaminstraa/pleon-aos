package com.charaminstra.pleon.common

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun showKeyboard(view:View, context: Context) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, 0)
}
