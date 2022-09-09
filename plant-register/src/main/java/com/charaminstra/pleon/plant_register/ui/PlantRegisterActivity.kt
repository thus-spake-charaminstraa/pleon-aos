package com.charaminstra.pleon.plant_register.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.charaminstra.pleon.common_ui.CustomDialog
import com.charaminstra.pleon.plant_register.PlantRegisterViewModel
import com.charaminstra.pleon.plant_register.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantRegisterActivity : AppCompatActivity() {
    private val viewModel: PlantRegisterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_register)
    }
}

