package com.charaminstra.pleon.plant_register.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.plant_register.PlantRegisterViewModel
import com.charaminstra.pleon.plant_register.R
import com.charaminstra.pleon.plant_register.databinding.FragmentPlantRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantRegisterFragment : Fragment() {
    private val viewModel: PlantRegisterViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPlantRegisterBinding.inflate(inflater, container, false)
        val navController = this.findNavController()
        binding.backBtn.setOnClickListener {
            activity?.finish()
        }
//        binding.skipBtn.setOnClickListener {
//            startHomeActivity()
//        }
        binding.nextBtn.setOnClickListener {
            viewModel.setName(binding.nameInput.text.toString())
            viewModel.setSpecies(binding.speciesInput.text.toString())
            viewModel.setAdopt_date(binding.adoptDayInput.text.toString())
            viewModel.setWater_date(binding.waterDayInput.text.toString())
            navController.navigate(R.id.plant_register_fragment_to_plant_light_fragment)
        }
//        binding.cameraBtn.setOnClickListener {
//        }



        return binding.root
    }
}