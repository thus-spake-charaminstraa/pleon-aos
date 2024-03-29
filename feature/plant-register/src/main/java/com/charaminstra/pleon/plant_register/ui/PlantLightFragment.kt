package com.charaminstra.pleon.plant_register.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.common_ui.ErrorToast
import com.charaminstra.pleon.common.LightType
import com.charaminstra.pleon.plant_register.PlantRegisterViewModel
import com.charaminstra.pleon.plant_register.R
import com.charaminstra.pleon.plant_register.databinding.FragmentPlantLightBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantLightFragment : Fragment() {
    private val viewModel: PlantRegisterViewModel by activityViewModels()
    private lateinit var binding: FragmentPlantLightBinding
    private var isChecking: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=FragmentPlantLightBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val navController = this.findNavController()
        binding.plantLightBackBtn.setOnClickListener {
            navController.popBackStack()
        }
        buttonCheck()
        binding.lightNextBtn.setOnClickListener {
            if(!isChecking){
                ErrorToast(requireContext()).showMsgDown(resources.getString(R.string.plant_light_fragment_error),binding.lightThreeBtn.y+binding.lightThreeBtn.height)
            }else{
                navController.navigate(R.id.plant_light_fragment_to_plant_air_fragment)
            }
        }
        return binding.root
    }

    fun buttonCheck(){
        /* button selector */
        binding.lightOneBtn.setOnClickListener {
            viewModel.setLight(LightType.BRIGHT.apiString)
            isChecking = true
            binding.lightOneBtn.isSelected = true
            binding.lightTwoBtn.isSelected = false
            binding.lightThreeBtn.isSelected = false
            binding.lightFourBtn.isSelected = false
        }
        binding.lightTwoBtn.setOnClickListener {
            isChecking = true
            viewModel.setLight(LightType.HALF_BRIGHT.apiString)
            binding.lightOneBtn.isSelected = false
            binding.lightTwoBtn.isSelected = true
            binding.lightThreeBtn.isSelected = false
            binding.lightFourBtn.isSelected = false
        }
        binding.lightThreeBtn.setOnClickListener {
            isChecking = true
            viewModel.setLight(LightType.LAMP.apiString)
            binding.lightOneBtn.isSelected = false
            binding.lightTwoBtn.isSelected = false
            binding.lightThreeBtn.isSelected = true
            binding.lightFourBtn.isSelected = false

        }
        binding.lightFourBtn.setOnClickListener {
            isChecking = true
            viewModel.setLight(LightType.DARK.apiString)
            binding.lightOneBtn.isSelected = false
            binding.lightTwoBtn.isSelected = false
            binding.lightThreeBtn.isSelected = false
            binding.lightFourBtn.isSelected = true
        }
    }

    /* bright, half_bright, lamp, dark */
//        binding.lightOne.text = resources.getString(LightType.BRIGHT.descId)
//        binding.lightTwo.text = resources.getString(LightType.HALF_BRIGHT.descId)
//        binding.lightThree.text = resources.getString(LightType.LAMP.descId)
//        binding.lightFour.text = resources.getString(LightType.DARK.descId)

    override fun onResume() {
        super.onResume()
    }
}


