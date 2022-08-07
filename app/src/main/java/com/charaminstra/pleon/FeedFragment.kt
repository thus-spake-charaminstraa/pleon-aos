package com.charaminstra.pleon

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.charaminstra.pleon.adapter.FeedAdapter
import com.charaminstra.pleon.adapter.PlantAdapter
import com.charaminstra.pleon.databinding.FragmentFeedBinding
import com.charaminstra.pleon.viewmodel.FeedReadViewModel
import com.charaminstra.pleon.viewmodel.PlantsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : Fragment() {
    private val TAG = javaClass.name
    private val plantsViewModel: PlantsViewModel by viewModels()
    private val feedReadViewModel: FeedReadViewModel by viewModels()
    private lateinit var binding : FragmentFeedBinding
    private lateinit var plantAdapter: PlantAdapter
    private lateinit var feedAdapter: FeedAdapter
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
        binding.feedFilterRecyclerview.adapter = plantAdapter
        binding.feedRecyclerview.adapter = feedAdapter
        binding.writeBtn.setOnClickListener {
            navController.navigate(R.id.view_pager_fragment_to_feed_write_fragment)
        }
        binding.feedRecyclerview.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
    }

    override fun onResume() {
        super.onResume()
        //viewmodel update
        plantsViewModel.loadData()
        feedReadViewModel.loadData(null,null)
    }

    private fun initList() {
        plantAdapter = PlantAdapter()
        plantAdapter.setType("FEED_PLANT")
        plantAdapter.onItemClicked = { plantId ->
            val bundle = Bundle()
            bundle.putString("id", plantId)
            //navController.navigate(R.id.view_pager_fragment_to_plant_detail_fragment, bundle)
        }
        feedAdapter = FeedAdapter()
        feedAdapter.onItemClicked = { feedId ->

        }
    }

    private fun observeViewModel() {
        plantsViewModel.plantsList.observe(viewLifecycleOwner, Observer {
            plantAdapter.refreshItems(it)
        })
        feedReadViewModel.feedList.observe(viewLifecycleOwner, Observer {
            Log.i(TAG, it.toString())
            feedAdapter.refreshItems(it)
        })
    }
}
