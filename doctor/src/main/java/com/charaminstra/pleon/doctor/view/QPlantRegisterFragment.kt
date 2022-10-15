package com.charaminstra.pleon.doctor.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.common_ui.ErrorToast
import com.charaminstra.pleon.doctor.DoctorViewModel
import com.charaminstra.pleon.doctor.R
import com.charaminstra.pleon.doctor.databinding.FragmentQPlantRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QPlantRegisterFragment : Fragment() {
    private lateinit var binding : FragmentQPlantRegisterBinding
    private val viewModel : DoctorViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQPlantRegisterBinding.inflate(inflater, container, false)
        val navController = this.findNavController()
        binding.qPlantRegisterBackBtn.setOnClickListener{
            activity?.finish()
        }
        binding.plantRegisterQ1.setOnClickListener {
            it.isSelected = true
            binding.plantRegisterQ2.isSelected=false
        }
        binding.plantRegisterQ2.setOnClickListener {
            it.isSelected = true
            binding.plantRegisterQ1.isSelected=false
            ErrorToast(requireContext()).showMsgUp(resources.getString(R.string.no_register_error_msg),binding.qPlantRegisterNextBtn.y)
        }
        binding.qPlantRegisterNextBtn.setOnClickListener {
            viewModel.warmingPlantDoctorModel()
            viewModel.warmingPlantDetectionModel()
            if(binding.plantRegisterQ1.isSelected){
                navController.navigate(R.id.q_plant_register_fragment_to_q_plant_choose_fragment)
            }else if(binding.plantRegisterQ2.isSelected){
                navController.navigate(R.id.q_plant_register_fragment_to_camera_fragment)
            }else{
                ErrorToast(requireContext()).showMsgUp(resources.getString(R.string.question_error_msg),binding.qPlantRegisterNextBtn.y)
            }
        }
        return binding.root
    }

}