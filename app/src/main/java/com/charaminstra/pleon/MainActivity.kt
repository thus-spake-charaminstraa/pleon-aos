package com.charaminstra.pleon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate(savedInstanceState)
        prefs.setString("phone", "010-1234-1234")
        prefs.setString("token", "012341234")
//        Log.i("pleon", prefs.getString("phone",""))
        setContentView(R.layout.activity_main)
    }
}