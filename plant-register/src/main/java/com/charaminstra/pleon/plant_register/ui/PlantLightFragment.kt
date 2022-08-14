package com.charaminstra.pleon.plant_register.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.plant_register.LightType
import com.charaminstra.pleon.plant_register.PlantIdViewModel
import com.charaminstra.pleon.plant_register.R
import com.charaminstra.pleon.plant_register.databinding.FragmentPlantLightBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantLightFragment : Fragment() {
    private val viewModel: PlantIdViewModel by activityViewModels()
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

        /* bright, half_bright, lamp, dark */
        binding.lightOne.text = LightType.BRIGHT.desc
        binding.lightTwo.text = LightType.HALF_BRIGHT.desc
        binding.lightThree.text = LightType.LAMP.desc
        binding.lightFour.text = LightType.DARK.desc

        radioGroupSet()
        setLightType()
        binding.completeBtn.setOnClickListener {
            if(setLightType())
                navController.navigate(R.id.plant_light_fragment_to_plant_air_fragment)
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
    private fun setLightType(): Boolean{
        if (mCheckedId == binding.lightOne.id) {
            viewModel.setLight(LightType.BRIGHT.apiString)
            return true
        } else if (mCheckedId == binding.lightTwo.id) {
            viewModel.setLight(LightType.HALF_BRIGHT.apiString)
            return true
        } else if (mCheckedId == binding.lightThree.id) {
            viewModel.setLight(LightType.LAMP.apiString)
            return true
        } else if (mCheckedId == binding.lightFour.id) {
            viewModel.setLight(LightType.DARK.apiString)
            return true
        }else
            return false
    }
}


