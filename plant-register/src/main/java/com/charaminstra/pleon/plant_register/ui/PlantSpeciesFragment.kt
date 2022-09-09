package com.charaminstra.pleon.plant_register.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.charaminstra.pleon.common_ui.CustomDialog
import com.charaminstra.pleon.plant_register.PlantSearchAdapter
import com.charaminstra.pleon.plant_register.R
import com.charaminstra.pleon.plant_register.databinding.FragmentPlantSpeciesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantSpeciesFragment : Fragment() {
    private lateinit var binding: FragmentPlantSpeciesBinding
    private lateinit var adapter: PlantSearchAdapter
    private val viewModel: PlantSearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlantSpeciesBinding.inflate(layoutInflater)
        val navController = this.findNavController()

        initList()
        observeViewModel()
        viewModel.getPlantSpecies()
        binding.plantSpeciesRecyclerview.adapter = adapter
        binding.plantSpeciesRecyclerview.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))

        binding.plantSpeciesSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            //확인 누르면
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            //글자 칠때 마다 변함
            override fun onQueryTextChange(newText: String): Boolean {
                if(newText.isEmpty()){
                    binding.plantSpeciesEt.visibility = View.VISIBLE
                    binding.plantSpeciesRecyclerview.visibility = View.GONE
                }else{
                    binding.plantSpeciesEt.visibility = View.GONE
                    binding.plantSpeciesRecyclerview.visibility = View.VISIBLE
                }
                viewModel.searchFilter(newText)
                return true
            }
        })

        binding.plantSpeciesSkipBtn.setOnClickListener {
            val dlg = CustomDialog(requireContext())
            dlg.setOnOKClickedListener {
                activity?.finish()
            }
            dlg.start(
                resources.getString(R.string.plant_register_skip_dialog_title),
                resources.getString(R.string.plant_register_skip_dialog_desc),
                resources.getString(R.string.plant_register_skip_dialog_cancel_btn),
                resources.getString(R.string.plant_register_skip_dialog_skip_btn)
            )
        }
        return binding.root
    }

    private fun initList() {
        adapter = PlantSearchAdapter()
        adapter.onItemClicked = { name ->
            binding.plantSpeciesEt.visibility = View.VISIBLE
            binding.plantSpeciesEt.setText(name)
            binding.plantSpeciesRecyclerview.visibility = View.GONE

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