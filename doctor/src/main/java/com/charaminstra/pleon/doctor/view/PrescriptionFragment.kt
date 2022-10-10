package com.charaminstra.pleon.doctor.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.charaminstra.pleon.doctor.DoctorViewModel
import com.charaminstra.pleon.doctor.R
import com.charaminstra.pleon.doctor.databinding.FragmentPrescriptionBinding
import com.charaminstra.pleon.doctor.databinding.FragmentQPlantRegisterBinding

class PrescriptionFragment : Fragment() {
    private lateinit var binding : FragmentPrescriptionBinding
    private val viewModel : DoctorViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrescriptionBinding.inflate(inflater, container, false)

        initObservers()
        return binding.root
    }

    private fun initObservers(){
        viewModel.causesList.observe(viewLifecycleOwner){
            if(it.size == 1){
                binding.cause2.root.visibility = View.GONE
                binding.cause1.causeNum.text = "1"
                binding.cause1.causeTxt.text = it[0].cause_ko

                binding.solution2.root.visibility = View.GONE
                binding.solution1.solutionNum.text = "1"
                binding.solution1.solutionTxt.text = it[0].guide
            }else if(it.size == 2){
                binding.cause1.causeNum.text = "1"
                binding.cause2.causeNum.text = "2"
                binding.cause1.causeTxt.text = it[0].cause_ko
                binding.cause2.causeTxt.text = it[1].cause_ko
                binding.solution1.solutionNum.text = "1"
                binding.solution2.solutionNum.text = "2"
                binding.solution1.solutionTxt.text = it[0].guide
                binding.solution2.solutionTxt.text = it[1].guide
            }
        }
    }

}