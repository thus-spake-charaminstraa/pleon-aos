package com.charaminstra.pleon.garden.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.common.*
import com.charaminstra.pleon.garden.GardenPlantAdapter
import com.charaminstra.pleon.garden.R
import com.charaminstra.pleon.garden.databinding.FragmentGardenBinding
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GardenFragment : Fragment() {
    private val TAG = javaClass.name
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private lateinit var binding: FragmentGardenBinding
    private lateinit var adapter: GardenPlantAdapter
    private val viewModel: PlantsViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController()

        firebaseAnalytics= FirebaseAnalytics.getInstance(requireContext())

        // logging
        val bundle = Bundle()
        bundle.putString(CLASS_NAME, TAG)
        firebaseAnalytics.logEvent(GARDEN_VIEW , bundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGardenBinding.inflate(layoutInflater)
        binding.gardenAddBtn.setOnClickListener {
            startPlantRegisterActivity(requireContext())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        observeViewModel()
        binding.recyclerview.adapter = adapter
    }

    private fun initList() {
        adapter = GardenPlantAdapter()
        adapter.onItemClicked = { plantId ->
            val bundle = Bundle()
            bundle.putString("id", plantId)
            navController.navigate(R.id.garden_fragment_to_plant_detail_fragment, bundle)

            // logging
            val loggingBundle = Bundle()
            loggingBundle.putString(CLASS_NAME, TAG)
            firebaseAnalytics.logEvent(PLANT_ITEM_CLICK,loggingBundle)
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

    fun startPlantRegisterActivity(context: Context) {
        val intent = Intent(
            context,
            Class.forName("com.charaminstra.pleon.plant_register.ui.PlantRegisterActivity")
        )
        startActivity(intent)

        // logging
        val loggingBundle = Bundle()
        loggingBundle.putString(CLASS_NAME, TAG)
        firebaseAnalytics.logEvent(PLANT_REGISTER_BTN_CLICK  , loggingBundle)
    }
}