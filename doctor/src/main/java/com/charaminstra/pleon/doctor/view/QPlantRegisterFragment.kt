package com.charaminstra.pleon.doctor.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.common.CLASS_NAME
import com.charaminstra.pleon.common.DOCTOR_NO_REGISTER_PLANT_CLICK
import com.charaminstra.pleon.common.DOCTOR_REGISTER_PLANT_CLICK
import com.charaminstra.pleon.common_ui.ErrorToast
import com.charaminstra.pleon.common_ui.InfoToast
import com.charaminstra.pleon.doctor.DoctorViewModel
import com.charaminstra.pleon.doctor.R
import com.charaminstra.pleon.doctor.databinding.FragmentQPlantRegisterBinding
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QPlantRegisterFragment : Fragment() {
    private val TAG = javaClass.name
    private lateinit var binding : FragmentQPlantRegisterBinding
    private val viewModel : DoctorViewModel by activityViewModels()
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics= FirebaseAnalytics.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQPlantRegisterBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        viewModel.getPlantList()

        val navController = this.findNavController()

        binding.qPlantRegisterBackBtn.setOnClickListener{
            activity?.finish()
        }
        binding.plantRegisterQ1.setOnClickListener {
            Log.i(TAG, "plant count -> "+viewModel.plantsCount.value)
            if(viewModel.plantsCount.value!! == 0){
                ErrorToast(requireContext()).showMsgUp(resources.getString(R.string.no_register_error_msg),binding.qPlantRegisterNextBtn.y)
            }else{
                it.isSelected = true
                binding.plantRegisterQ2.isSelected=false
            }
        }
        binding.plantRegisterQ2.setOnClickListener {
            it.isSelected = true
            binding.plantRegisterQ1.isSelected=false
            InfoToast(requireContext()).showMsgUp(resources.getString(R.string.no_register_info),binding.qPlantRegisterNextBtn.y)
        }
        binding.qPlantRegisterNextBtn.setOnClickListener {
            viewModel.warmingPlantDoctorModel()
            viewModel.warmingPlantDetectionModel()
            if(binding.plantRegisterQ1.isSelected){
                // logging
                val bundle = Bundle()
                bundle.putString(CLASS_NAME, TAG)
                firebaseAnalytics.logEvent(DOCTOR_NO_REGISTER_PLANT_CLICK , bundle)

                navController.navigate(R.id.q_plant_register_fragment_to_q_plant_choose_fragment)
            }else if(binding.plantRegisterQ2.isSelected){
                // logging
                val bundle = Bundle()
                bundle.putString(CLASS_NAME, TAG)
                firebaseAnalytics.logEvent(DOCTOR_REGISTER_PLANT_CLICK , bundle)

                navController.navigate(R.id.q_plant_register_fragment_to_camera_fragment)
            }else{
                ErrorToast(requireContext()).showMsgUp(resources.getString(R.string.question_error_msg),binding.qPlantRegisterNextBtn.y)
            }
        }
    }

    private fun initObservers(){
        viewModel.plantsCount.observe(viewLifecycleOwner, Observer {
        })
    }
}