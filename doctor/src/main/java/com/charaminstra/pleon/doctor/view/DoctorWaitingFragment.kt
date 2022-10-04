package com.charaminstra.pleon.doctor.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
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
        //viewModel.postPlantDetectionModel()
        return inflater.inflate(R.layout.fragment_doctor_waiting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.plantDetectionSuccess.observe(viewLifecycleOwner, Observer{
//            if(it == true ){
//                navController.navigate(R.id.plant_detection_waiting_fragment_to_plant_detection_result_fragment)
//                viewModel.clearPlantDetectionSuccess()
//            }else if(it == false){
//                navController.navigate(R.id.plant_detection_waiting_fragment_to_plant_detection_error_fragment)
//                viewModel.clearPlantDetectionSuccess()
//            }else{
//
//            }
//        })
    }

    override fun onResume() {
        super.onResume()
    }
}