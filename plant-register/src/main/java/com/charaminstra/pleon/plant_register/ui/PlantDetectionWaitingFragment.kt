package com.charaminstra.pleon.plant_register.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.charaminstra.pleon.plant_register.PlantRegisterViewModel
import com.charaminstra.pleon.plant_register.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantDetectionWaitingFragment : Fragment() {
    private val TAG = javaClass.name
    private val viewModel: PlantRegisterViewModel by activityViewModels()
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = this.findNavController()
        initObservers()
        viewModel.postPlantDetectionModel()
        return inflater.inflate(R.layout.fragment_plant_detection_waiting, container, false)
    }

    private fun initObservers(){
        viewModel.plantDetectionSuccess.observe(viewLifecycleOwner, Observer{
            if(it){
                navController.navigate(R.id.plant_detection_waiting_fragment_to_plant_detection_result_fragment)
            }else{
                navController.navigate(R.id.plant_detection_waiting_fragment_to_plant_detection_error_fragment)
            }
        })
    }
}