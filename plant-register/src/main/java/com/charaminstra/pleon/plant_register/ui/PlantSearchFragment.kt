package com.charaminstra.pleon.plant_register.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.foundation.model.PlantSpeciesDataObject
import com.charaminstra.pleon.plant_register.PlantSearchAdapter
import com.charaminstra.pleon.plant_register.databinding.FragmentPlantSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

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

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            //확인 누르면
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            //글자 칠때 마다 변함
            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.searchFilter(newText)
                return true
            }
        })

        return binding.root
    }

    private fun initList() {
        adapter = PlantSearchAdapter()
        adapter.onItemClicked = { name ->
            navController.previousBackStackEntry?.savedStateHandle?.set("plant_species", name)
            navController.popBackStack()
        }
    }

    private fun observeViewModel() {
        viewModel.plantSpeciesList.observe(viewLifecycleOwner, Observer {
            adapter.refreshItems(it)
        })
        viewModel.searchResult.observe(viewLifecycleOwner, Observer {
            adapter.refreshItems(it)
        })
    }
}