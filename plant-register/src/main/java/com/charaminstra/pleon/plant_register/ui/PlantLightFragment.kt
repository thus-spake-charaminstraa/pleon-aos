package com.charaminstra.pleon.plant_register.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.common_ui.showErrorToast
import com.charaminstra.pleon.plant_register.LightType
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
                Toast(activity).showErrorToast(resources.getString(R.string.plant_light_fragment_error),binding.lightThreeBtn.y+binding.lightThreeBtn.height,requireActivity())
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
            binding.lightOneBtn.background = resources.getDrawable(R.drawable.check_button)
            binding.lightTwoBtn.background = resources.getDrawable(R.drawable.uncheck_button)
            binding.lightThreeBtn.background = resources.getDrawable(R.drawable.uncheck_button)
            binding.lightFourBtn.background = resources.getDrawable(R.drawable.uncheck_button)
        }
        binding.lightTwoBtn.setOnClickListener {
            isChecking = true
            viewModel.setLight(LightType.HALF_BRIGHT.apiString)
            binding.lightOneBtn.background = resources.getDrawable(R.drawable.uncheck_button)
            binding.lightTwoBtn.background = resources.getDrawable(R.drawable.check_button)
            binding.lightThreeBtn.background = resources.getDrawable(R.drawable.uncheck_button)
            binding.lightFourBtn.background = resources.getDrawable(R.drawable.uncheck_button)
        }
        binding.lightThreeBtn.setOnClickListener {
            isChecking = true
            viewModel.setLight(LightType.LAMP.apiString)
            binding.lightOneBtn.background = resources.getDrawable(R.drawable.uncheck_button)
            binding.lightTwoBtn.background = resources.getDrawable(R.drawable.uncheck_button)
            binding.lightThreeBtn.background = resources.getDrawable(R.drawable.check_button)
            binding.lightFourBtn.background = resources.getDrawable(R.drawable.uncheck_button)
        }
        binding.lightFourBtn.setOnClickListener {
            isChecking = true
            viewModel.setLight(LightType.DARK.apiString)
            binding.lightOneBtn.background = resources.getDrawable(R.drawable.uncheck_button)
            binding.lightTwoBtn.background = resources.getDrawable(R.drawable.uncheck_button)
            binding.lightThreeBtn.background = resources.getDrawable(R.drawable.uncheck_button)
            binding.lightFourBtn.background = resources.getDrawable(R.drawable.check_button)
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


