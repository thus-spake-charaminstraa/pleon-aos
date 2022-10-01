package com.charaminstra.pleon.plant_register.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.plant_register.PlantRegisterViewModel
import com.charaminstra.pleon.plant_register.databinding.FragmentPlantDetectionErrorBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantDetectionErrorFragment : Fragment() {

    private val TAG = javaClass.name

    private lateinit var binding: FragmentPlantDetectionErrorBinding
    private val viewModel: PlantRegisterViewModel by activityViewModels()
    lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlantDetectionErrorBinding.inflate(layoutInflater)
        navController = this.findNavController()

        return binding.root
    }

}