package com.charaminstra.pleon.doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.doctor.databinding.FragmentQPlantRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QPlantRegisterFragment : Fragment() {
    private lateinit var binding : FragmentQPlantRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQPlantRegisterBinding.inflate(inflater, container, false)
        val navController = this.findNavController()
        binding.qPlantRegisterBackBtn.setOnClickListener{
            navController.popBackStack()
        }
        binding.plantRegisterQ1.setOnClickListener {
            it.isSelected = true
            binding.plantRegisterQ2.isSelected=false
        }
        binding.plantRegisterQ2.setOnClickListener {
            it.isSelected = true
            binding.plantRegisterQ1.isSelected=false
        }
        binding.qPlantRegisterNextBtn.setOnClickListener {
            navController.navigate(R.id.q_plant_register_fragment_to_q_plant_choose_fragment)
        }
        return binding.root
    }

}