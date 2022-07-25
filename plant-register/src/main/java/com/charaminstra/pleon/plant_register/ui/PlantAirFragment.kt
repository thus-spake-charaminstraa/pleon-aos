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
import com.charaminstra.pleon.plant_register.databinding.FragmentPlantAirBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantAirFragment : Fragment() {
    private val viewModel: PlantRegisterViewModel by activityViewModels()
    private lateinit var binding: FragmentPlantAirBinding
    private var isChecking: Boolean = true
    private var mCheckedId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=FragmentPlantAirBinding.inflate(layoutInflater)

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

            }
        }
        return binding.root
    }

    fun radioGroupSet(){
        binding.airRg1.setOnCheckedChangeListener { radioGroup, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                binding.airRg2.clearCheck()
                mCheckedId = checkedId
            }
            isChecking = true
        }
        binding.airRg2.setOnCheckedChangeListener { radioGroup, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                binding.airRg1.clearCheck()
                mCheckedId = checkedId
            }
            isChecking = true
        }
    }
    fun setLihgtType() : Boolean {
        if (mCheckedId == binding.airOne.id) {
            viewModel.setAir(R.string.air_one.toString())
            Toast.makeText(requireContext(), "type1", Toast.LENGTH_SHORT).show()
            return true
        } else if (mCheckedId == binding.airTwo.id) {
            viewModel.setAir(R.string.air_two.toString())
            Toast.makeText(requireContext(), "type2", Toast.LENGTH_SHORT).show()
            return true
        } else if (mCheckedId == binding.airThree.id) {
            viewModel.setAir(R.string.air_three.toString())
            Toast.makeText(requireContext(), "type3", Toast.LENGTH_SHORT).show()
            return true
        } else{
            return false
        }
    }
}


