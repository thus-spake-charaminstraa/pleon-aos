package com.charaminstra.pleon.common

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

const val REQUEST_GALLERY = 2000
const val REQUEST_TAKE_PHOTO = 3000
const val PERMISSION = 1000

object RequestPermission: AppCompatActivity() {

    fun requestPermission(activity: Activity){
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.CAMERA),
            PERMISSION
        )
    }

    fun checkPermission(activity: Activity): Boolean {
        val permissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
        return permissionCheck == PackageManager.PERMISSION_GRANTED
    }
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        Log.i("permission :requestCode: ",requestCode.toString())
//        Log.i("permission :grantResults: ",grantResults.toString())
//        if (requestCode == PERMISSION ) {
//            if (grantResults.isNotEmpty()
//                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Log.i("permission :","granted")
//                //openCamera()
//            } else {
//                Log.i("permission :","no granted")
//                //ErrorToast(requireContext()).showCameraPermission()
//            }
//            return
//        }
//    }
}