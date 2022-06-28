package com.charaminstra.pleon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation.findNavController
import com.charaminstra.pleon.login.LoginFragment

class MainActivity : AppCompatActivity() {
//    companion object {
//        lateinit var prefs: PreferenceUtil
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}