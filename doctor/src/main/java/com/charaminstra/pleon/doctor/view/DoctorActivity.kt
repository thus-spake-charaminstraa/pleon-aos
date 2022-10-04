package com.charaminstra.pleon.doctor.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.charaminstra.pleon.doctor.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor)
    }
}