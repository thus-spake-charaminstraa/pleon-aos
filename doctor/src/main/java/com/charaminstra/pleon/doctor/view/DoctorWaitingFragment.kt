package com.charaminstra.pleon.doctor.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.doctor.DoctorViewModel
import com.charaminstra.pleon.doctor.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorWaitingFragment : Fragment() {
    private val TAG = javaClass.name
    private val viewModel: DoctorViewModel by activityViewModels()
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = this.findNavController()
        viewModel.postPlantDoctorModel()
        return inflater.inflate(R.layout.fragment_doctor_waiting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.plantDoctorSuccess.observe(viewLifecycleOwner, Observer{
            navController.navigate(R.id.doctor_waiting_fragment_to_prescription_fragment)
        })
    }
}