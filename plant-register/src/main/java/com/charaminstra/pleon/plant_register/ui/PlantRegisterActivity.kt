package com.charaminstra.pleon.plant_register.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.charaminstra.pleon.plant_register.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_register)
    }
}