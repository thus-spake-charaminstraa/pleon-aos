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
import com.charaminstra.pleon.plant_register.PlantRegisterViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlantDetailFragment : Fragment() {
    private val viewModel: PlantRegisterViewModel by viewModels()
    private lateinit var binding : FragmentPlantDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentPlantDetailBinding.inflate(layoutInflater)
        lateinit var plantId : String
        val navController = this.findNavController()
        binding.backBtn.setOnClickListener {
            navController.popBackStack()
        }

        /*plant Id*/
        arguments?.getString("id")?.let {
            plantId = it
            viewModel.loadData(plantId)
            binding.editBtn.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("id",plantId)
                navController.navigate(R.id.plant_detail_to_plant_edit_fragment,bundle)
            }
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