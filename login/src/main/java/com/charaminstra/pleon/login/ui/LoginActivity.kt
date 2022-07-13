package com.charaminstra.pleon.login.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.charaminstra.pleon.foundation.PreferenceUtil
import com.charaminstra.pleon.login.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs=PreferenceUtil(this)
        setContentView(R.layout.activity_login)
    }
}