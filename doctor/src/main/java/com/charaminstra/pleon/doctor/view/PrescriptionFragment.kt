package com.charaminstra.pleon.doctor.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.charaminstra.pleon.common.CLASS_NAME
import com.charaminstra.pleon.common.DOCTOR_PRESCRIPTION_COMPLETE_CLICK
import com.charaminstra.pleon.doctor.DoctorViewModel
import com.charaminstra.pleon.doctor.databinding.FragmentPrescriptionBinding
import com.google.firebase.analytics.FirebaseAnalytics

class PrescriptionFragment : Fragment() {
    private val TAG = javaClass.name
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var binding : FragmentPrescriptionBinding
    private lateinit var causeAdapter: com.charaminstra.pleon.feed_common.CauseAdapter
    private lateinit var solutionAdapter: com.charaminstra.pleon.feed_common.SolutionAdapter
    private lateinit var symptomAdapter: com.charaminstra.pleon.feed_common.SymptomAdapter
    private val viewModel : DoctorViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics= FirebaseAnalytics.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrescriptionBinding.inflate(inflater, container, false)
        if(viewModel.plantDoctorSuccess.value == true){
            initList()
            binding.causeRecyclerview.adapter = causeAdapter
            binding.solutionRecyclerview.adapter = solutionAdapter
            binding.symptomRecyclerview.adapter = symptomAdapter
        }else if(viewModel.plantDoctorSuccess.value == false){
            binding.prescriptionResult.visibility = View.GONE
            binding.prescriptionNoResult.root.visibility = View.VISIBLE
        }
        binding.prescriptionCompleteBtn.setOnClickListener {
            // logging
            val bundle = Bundle()
            bundle.putString(CLASS_NAME, TAG)
            firebaseAnalytics.logEvent(DOCTOR_PRESCRIPTION_COMPLETE_CLICK , bundle)

            activity?.finish()
        }
        initObservers()
        return binding.root
    }

    private fun initList(){
        causeAdapter = com.charaminstra.pleon.feed_common.CauseAdapter()
        solutionAdapter = com.charaminstra.pleon.feed_common.SolutionAdapter()
        symptomAdapter = com.charaminstra.pleon.feed_common.SymptomAdapter()
    }

    private fun initObservers(){
        viewModel.causesList.observe(viewLifecycleOwner){
            causeAdapter.setItemList(it)
            solutionAdapter.setItemList(it)
        }
        viewModel.symptomsList.observe(viewLifecycleOwner){
            symptomAdapter.setItemList(it)
        }
        viewModel.plantName.observe(viewLifecycleOwner){
            binding.prescriptionPatientName.text = it
        }
    }

}