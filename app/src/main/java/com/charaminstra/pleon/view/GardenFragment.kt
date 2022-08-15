package com.charaminstra.pleon.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.R
import com.charaminstra.pleon.adapter.PlantAdapter
import com.charaminstra.pleon.databinding.FragmentGardenBinding
import com.charaminstra.pleon.plant_register.ui.PlantRegisterActivity
import com.charaminstra.pleon.viewmodel.PlantsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GardenFragment : Fragment() {
    private val viewModel: PlantsViewModel by viewModels()
    private lateinit var binding: FragmentGardenBinding
    private lateinit var navController: NavController
    private lateinit var adapter: PlantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGardenBinding.inflate(layoutInflater)
        navController = this.findNavController()
        binding.gardenFragmentTitle.text = "Garden"

        binding.circleBtn.setOnClickListener {
            val intent = Intent(context, PlantRegisterActivity::class.java)
            intent.putExtra("from", "main")
            startActivity(intent)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        observeViewModel()
        //authViewModel.loadAuth()
        binding.recyclerview.adapter = adapter
    }

    private fun initList() {
        adapter = PlantAdapter()
        adapter.setType("GARDEN_PLANT")
        adapter.onItemClicked = { plantId ->
            Log.i("plant id", plantId)
            val bundle = Bundle()
            bundle.putString("id", plantId)
            navController.navigate(R.id.view_pager_fragment_to_plant_detail_fragment, bundle)
        }
    }

    private fun observeViewModel() {
        viewModel.plantsList.observe(viewLifecycleOwner, Observer {
            adapter.refreshItems(it)
        })
    }

    override fun onResume() {
        super.onResume()
        //viewmodel update
        viewModel.loadData()
    }
}