package com.charaminstra.pleon.plant_register.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.plant_register.PlantRegisterViewModel
import com.charaminstra.pleon.plant_register.R
import com.charaminstra.pleon.plant_register.databinding.FragmentPlantLightBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantLightFragment : Fragment() {
    private val viewModel: PlantRegisterViewModel by activityViewModels()
    private lateinit var binding: FragmentPlantLightBinding
    private var isChecking: Boolean = true
    private var mCheckedId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=FragmentPlantLightBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val navController = this.findNavController()
        binding.backBtn.setOnClickListener {
            navController.popBackStack()
        }
        radioGroupSet()
        binding.completeBtn.setOnClickListener {
            if(setLihgtType()){
                navController.navigate(R.id.plant_light_fragment_to_plant_air_fragment)
            }
        }
        return binding.root
    }
    fun radioGroupSet(){
        binding.lightRg1.setOnCheckedChangeListener { radioGroup, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                binding.lightRg2.clearCheck()
                mCheckedId = checkedId
            }
            isChecking = true
        }
        binding.lightRg2.setOnCheckedChangeListener { radioGroup, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                binding.lightRg1.clearCheck()
                mCheckedId = checkedId
            }
            isChecking = true
        }
    }
    fun setLihgtType() : Boolean {
        if (mCheckedId == binding.lightOne.id) {
            viewModel.setLight(R.string.light_one.toString())
            Toast.makeText(requireContext(), "type1", Toast.LENGTH_SHORT).show()
            return true
        } else if (mCheckedId == binding.lightTwo.id) {
            viewModel.setLight(R.string.light_two.toString())
            Toast.makeText(requireContext(), "type2", Toast.LENGTH_SHORT).show()
            return true
        } else if (mCheckedId == binding.lightThree.id) {
            viewModel.setLight(R.string.light_three.toString())
            Toast.makeText(requireContext(), "type3", Toast.LENGTH_SHORT).show()
            return true
        } else if (mCheckedId == binding.lightFour.id) {
            viewModel.setLight(R.string.light_four.toString())
            Toast.makeText(requireContext(), "type4", Toast.LENGTH_SHORT).show()
            return true
        } else{
            return false
        }
    }
}


