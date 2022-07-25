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

        binding.rg1.setOnCheckedChangeListener { radioGroup, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                binding.rg2.clearCheck()
                mCheckedId = checkedId
            }
            isChecking = true
        }
        binding.rg2.setOnCheckedChangeListener { radioGroup, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                binding.rg1.clearCheck()
                mCheckedId = checkedId
            }
            isChecking = true
        }

        binding.completeBtn.setOnClickListener {
            showType(it)
            //navController.navigate(R.id.plant_place_fragment_to_welcome_fragment)
        }
        return binding.root
    }
    fun showType(view: View?) {
        if (mCheckedId === binding.lightOne.id) {
            Toast.makeText(requireContext(), "type1", Toast.LENGTH_SHORT).show()
        } else if (mCheckedId === binding.lightTwo.id) {
            Toast.makeText(requireContext(), "type2", Toast.LENGTH_SHORT).show()
        } else if (mCheckedId === binding.lightThree.id) {
            Toast.makeText(requireContext(), "type3", Toast.LENGTH_SHORT).show()
        } else if (mCheckedId === binding.lightFour.id) {
            Toast.makeText(requireContext(), "type4", Toast.LENGTH_SHORT).show()
        }
    }
}


