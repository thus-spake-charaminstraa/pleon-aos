package com.charaminstra.pleon.plant_register

import android.content.Context
import android.media.ExifInterface
import android.net.Uri
import java.io.IOException

fun getOrientation(context: Context, uri: Uri): Int {
    // uri -> inputStream
    val inputStream = context.contentResolver.openInputStream(uri)
    val exif: ExifInterface? = try {
        ExifInterface(inputStream!!)
    } catch (e: IOException) {
        e.printStackTrace()
        return -1
    }
    inputStream.close()

    // 회전된 각도 알아내기
    val orientation = exif?.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
    if (orientation != -1) {
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> return 90
            ExifInterface.ORIENTATION_ROTATE_180 -> return 180
            ExifInterface.ORIENTATION_ROTATE_270 -> return 270
        }
    }
    return 0
}

fun rotateBitmap(){

}