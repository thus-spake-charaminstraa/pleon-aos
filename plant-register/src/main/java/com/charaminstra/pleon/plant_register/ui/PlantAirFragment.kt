package com.charaminstra.pleon.plant_register.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.common_ui.ErrorToast
import com.charaminstra.pleon.plant_register.AirType
import com.charaminstra.pleon.plant_register.PlantRegisterViewModel
import com.charaminstra.pleon.plant_register.R
import com.charaminstra.pleon.plant_register.databinding.FragmentPlantAirBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantAirFragment : Fragment() {
    private val viewModel: PlantRegisterViewModel by activityViewModels()
    private lateinit var binding: FragmentPlantAirBinding
    private var isChecking: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= FragmentPlantAirBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val navController = this.findNavController()
        binding.plantAirBackBtn.setOnClickListener {
            navController.popBackStack()
        }
        buttonCheck()
        binding.airNextBtn.setOnClickListener {
            if(!isChecking){
                ErrorToast(requireContext()).showMsg(resources.getString(R.string.plant_air_fragment_error),binding.airThreeBtn.y+binding.airThreeBtn.height)
            }else{
                viewModel.postPlant()
                activity?.finish()
            }
        }
        return binding.root
    }

    fun buttonCheck(){
        /* button selector */
        binding.airOneBtn.setOnClickListener {
            viewModel.setAir(AirType.YES.apiString)
            isChecking = true
            binding.airOneBtn.isSelected = true
            binding.airTwoBtn.isSelected = false
            binding.airThreeBtn.isSelected = false
        }
        binding.airTwoBtn.setOnClickListener {
            isChecking = true
            viewModel.setAir(AirType.WINDOW.apiString)
            binding.airOneBtn.isSelected = false
            binding.airTwoBtn.isSelected = true
            binding.airThreeBtn.isSelected = false
        }
        binding.airThreeBtn.setOnClickListener {
            isChecking = true
            viewModel.setAir(AirType.NO.apiString)
            binding.airOneBtn.isSelected = false
            binding.airTwoBtn.isSelected = false
            binding.airThreeBtn.isSelected = true

        }
    }

    /* bright, half_bright, lamp, dark */
//        binding.airOne.text = resources.getString(LightType.BRIGHT.descId)
//        binding.airTwo.text = resources.getString(LightType.HALF_BRIGHT.descId)
//        binding.airThree.text = resources.getString(LightType.LAMP.descId)
//        binding.airFour.text = resources.getString(LightType.DARK.descId)

    override fun onResume() {
        super.onResume()
    }
}


