package com.charaminstra.pleon

import android.content.Intent
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
import com.charaminstra.pleon.adapter.CommonAdapter
import com.charaminstra.pleon.databinding.FragmentFeedBinding
import com.charaminstra.pleon.databinding.FragmentGardenBinding
import com.charaminstra.pleon.plant_register.ui.PlantRegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : Fragment() {
    private val TAG = javaClass.name
    private val plantsViewModel: PlantsViewModel by viewModels()
    private val feedReadViewModel: FeedReadViewModel by viewModels()
    private lateinit var binding : FragmentFeedBinding
    private lateinit var adapter: CommonAdapter
    private lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(layoutInflater)
        navController = this.findNavController()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        observeViewModel()
        binding.filterRecyclerview.adapter = adapter
        binding.writeBtn.setOnClickListener {
            navController.navigate(R.id.view_pager_fragment_to_feed_write_fragment)
        }
    }

    private fun initList() {
        adapter = CommonAdapter()
        adapter.setType("FEED_PLANT")
        adapter.onItemClicked = { plantId ->
            val bundle = Bundle()
            bundle.putString("id", plantId)
            //navController.navigate(R.id.view_pager_fragment_to_plant_detail_fragment, bundle)
        }
    }

    private fun observeViewModel() {
        plantsViewModel.plantsList.observe(viewLifecycleOwner, Observer {
            adapter.refreshItems(it)
        })
        feedReadViewModel.feedList.observe(viewLifecycleOwner, Observer {
            Log.i(TAG, it.toString())
        })
    }

    override fun onResume() {
        super.onResume()
        //viewmodel update
        plantsViewModel.loadData()
        feedReadViewModel.loadData()
    }
}