package com.charaminstra.pleon.plant_register.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlantDetectionResultBinding.inflate(layoutInflater)
        Glide.with(requireContext()).load(viewModel.plantDetectionUrlResponse.value).into(binding.plantDetectionResultImg)
        binding.plantDetectionResultLabel.text = viewModel.plantDetectionResultLabel.value
        return binding.root
    }
}