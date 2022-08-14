package com.charaminstra.pleon.plant_register.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.plant_register.PlantSearchAdapter
import com.charaminstra.pleon.plant_register.databinding.FragmentPlantSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantSearchFragment : Fragment() {

    private val viewModel: PlantSearchViewModel by viewModels()
    private lateinit var binding: FragmentPlantSearchBinding
    private lateinit var navController: NavController
    private lateinit var adapter: PlantSearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlantSearchBinding.inflate(layoutInflater)
        navController = this.findNavController()

        binding.backBtn.setOnClickListener{
            navController.popBackStack()
        }

        initList()
        observeViewModel()
        viewModel.getPlantSpecies()
        binding.plantSpeciesRecyclerview.adapter = adapter

        return binding.root
    }

    private fun initList() {
        adapter = PlantSearchAdapter()
        adapter.onItemClicked = { name ->
            Log.i("plant name", name)
            val bundle = Bundle()
            bundle.putString("id", name)
            //navController.bac
        }
    }

    private fun observeViewModel() {
        viewModel.plantSpeciesList.observe(viewLifecycleOwner, Observer {
            adapter.refreshItems(it)
        })
    }

}