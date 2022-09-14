package com.charaminstra.pleon.common_ui

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import com.charaminstra.pleon.common_ui.databinding.CustomToastErrorLayoutBinding

class ErrorToast(val context: Context) {
    val binding = CustomToastErrorLayoutBinding.inflate(LayoutInflater.from(context))

    fun showMsg(message: String, relativeLocation: Float) {
        binding.toastText.text = message
        Toast(context).apply {
            setGravity(Gravity.TOP, 0, relativeLocation.toInt()+150)
            duration = Toast.LENGTH_SHORT
            view = binding.root
            show()
        }
    }

    fun showCameraPermission() {
        binding.toastText.text = context.resources.getString(R.string.camera_permission_msg)
        // use the application extension function
        Toast(context).apply {
            setGravity(Gravity.CENTER, 0, 400)
            duration = Toast.LENGTH_SHORT
            view = binding.root
            show()
        }
    }
}