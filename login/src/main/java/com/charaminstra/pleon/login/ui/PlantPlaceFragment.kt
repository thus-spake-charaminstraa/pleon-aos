package com.charaminstra.pleon.login.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.login.R
import com.charaminstra.pleon.login.databinding.FragmentPlantPlaceBinding

class PlantPlaceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPlantPlaceBinding.inflate(inflater, container, false)
        val navController = this.findNavController()
        binding.completeBtn.setOnClickListener {
            navController.navigate(R.id.plant_place_fragment_to_welcome_fragment)
        }
        return binding.root
    }

}