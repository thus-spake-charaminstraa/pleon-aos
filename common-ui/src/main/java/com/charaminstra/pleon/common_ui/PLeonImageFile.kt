package com.charaminstra.pleon.common_ui

import android.app.Activity
import android.os.Environment
import java.io.File

class PLeonImageFile(activity: Activity){
    lateinit var currentPhotoPath: String
    private val storageDir: File? = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    fun create(): File{
        return File.createTempFile(
            "image", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }
}