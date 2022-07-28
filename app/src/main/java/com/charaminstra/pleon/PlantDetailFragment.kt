package com.charaminstra.pleon

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.charaminstra.pleon.databinding.FragmentPlantDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlantDetailFragment : Fragment() {
    private val viewModel: PlantDetailViewModel by viewModels()
    private lateinit var binding : FragmentPlantDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentPlantDetailBinding.inflate(layoutInflater)

        val navController = this.findNavController()
        binding.backBtn.setOnClickListener {
            navController.popBackStack()
        }

        if(arguments?.getString("id")!=null){
            viewModel.loadData(arguments?.getString("id")!!)
        }
        viewModel.data.observe(this, Observer {
            binding.plantName.text = it.name
            Glide.with(binding.root)
                .load(it.thumbnail)
                .into(binding.plantImage)
            binding.plantSpeciesDesc.text = it.species
            binding.plantAdoptDayDesc.text = it.adopt_date
            binding.plantMood.text = "HAPPY"

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }


}