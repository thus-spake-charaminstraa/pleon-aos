package com.charaminstra.pleon.login

import android.content.Context
import android.content.Intent
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity

fun startHomeActivity(context: Context) {
    val intent = Intent(
        context,
        Class.forName("com.charaminstra.pleon.HomeActivity")
    )
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(context, intent, null)
}