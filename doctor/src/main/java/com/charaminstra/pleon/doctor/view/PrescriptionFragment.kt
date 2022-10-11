package com.charaminstra.pleon.doctor.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.charaminstra.pleon.doctor.CauseAdapter
import com.charaminstra.pleon.doctor.DoctorViewModel
import com.charaminstra.pleon.doctor.R
import com.charaminstra.pleon.doctor.databinding.FragmentPrescriptionBinding
import com.charaminstra.pleon.doctor.databinding.FragmentQPlantRegisterBinding

class PrescriptionFragment : Fragment() {
    private val TAG = javaClass.name
    private lateinit var binding : FragmentPrescriptionBinding
    private lateinit var causeAdapter: CauseAdapter
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
        }else if(viewModel.plantDoctorSuccess.value == false){
            binding.prescriptionResult.visibility = View.GONE
            binding.prescriptionNoResult.root.visibility = View.VISIBLE
        }

        initObservers()
        return binding.root
    }

    private fun initList(){
        causeAdapter = CauseAdapter()
    }

    private fun initObservers(){
        viewModel.causesList.observe(viewLifecycleOwner){
            causeAdapter.setItemList(it)
        }
    }

}