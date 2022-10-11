package com.charaminstra.pleon.doctor.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.charaminstra.pleon.doctor.CauseAdapter
import com.charaminstra.pleon.doctor.DoctorViewModel
import com.charaminstra.pleon.doctor.SolutionAdapter
import com.charaminstra.pleon.doctor.SymptomAdapter
import com.charaminstra.pleon.doctor.databinding.FragmentPrescriptionBinding

class PrescriptionFragment : Fragment() {
    private val TAG = javaClass.name
    private lateinit var binding : FragmentPrescriptionBinding
    private lateinit var causeAdapter: CauseAdapter
    private lateinit var solutionAdapter: SolutionAdapter
    private lateinit var symptomAdapter: SymptomAdapter
    private val viewModel : DoctorViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            activity?.finish()
        }
        initObservers()
        return binding.root
    }

    private fun initList(){
        causeAdapter = CauseAdapter()
        solutionAdapter = SolutionAdapter()
        symptomAdapter = SymptomAdapter()
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