package com.charaminstra.pleon.plant_register.ui

import android.os.Bundle
import android.util.Log
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
import com.charaminstra.pleon.plant_register.databinding.FragmentPlantDetectionResultBinding
import com.charaminstra.pleon.plant_register.databinding.FragmentPlantThumbnailBinding
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantDetectionResultFragment : Fragment() {

    private val TAG = javaClass.name

    private lateinit var binding: FragmentPlantDetectionResultBinding
    private val viewModel: PlantRegisterViewModel by activityViewModels()
    lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlantDetectionResultBinding.inflate(layoutInflater)
        navController = this.findNavController()

        initObservers()
        Glide.with(requireContext()).load(viewModel.plantDetectionUrlResponse.value).into(binding.plantDetectionResultImg)

        binding.plantDetectionResultOkBtn.setOnClickListener {
            navController.navigate(R.id.plant_detection_result_return)
        }

        return binding.root
    }

    private fun initObservers(){
        viewModel.plantDetectionResultLabel.observe(viewLifecycleOwner, Observer{
            binding.plantDetectionResultLabel.text = it
        })
        viewModel.plantDetectionResultPercent.observe(viewLifecycleOwner, Observer {
            binding.plantDetectionResultPercent.text = it
        })
    }
}