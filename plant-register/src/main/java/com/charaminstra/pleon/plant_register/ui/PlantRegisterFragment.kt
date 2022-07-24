package com.charaminstra.pleon.plant_register.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.plant_register.R
import com.charaminstra.pleon.plant_register.databinding.FragmentPlantRegisterBinding

class PlantRegisterFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPlantRegisterBinding.inflate(inflater, container, false)
        val navController = this.findNavController()
        binding.backBtn.setOnClickListener {
            navController.popBackStack()
        }
        binding.skipBtn.setOnClickListener {
            startHomeActivity()
        }
//        binding.nextBtn.setOnClickListener {
//            navController.navigate(R.id.plant_register_fragment_to_plant_place_fragment)
//        }
        binding.cameraBtn.setOnClickListener {
        }

        val plant_species = binding.speciesInput.text
        val plant_name = binding.nameInput.text
        val plant_adopt_day = binding.adoptDayInput.text
        val plant_water_day = binding.waterDayInput.text

        return binding.root
    }
    private fun startHomeActivity() {
        val intent = Intent(
            requireContext(),
            Class.forName("com.charaminstra.pleon.HomeActivity")
        )
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}