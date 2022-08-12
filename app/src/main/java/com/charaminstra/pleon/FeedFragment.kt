package com.charaminstra.pleon

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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.charaminstra.pleon.adapter.FeedTabAdapter
import com.charaminstra.pleon.adapter.PlantAdapter
import com.charaminstra.pleon.databinding.FragmentFeedBinding
import com.charaminstra.pleon.viewmodel.FeedViewModel
import com.charaminstra.pleon.viewmodel.PlantsViewModel
import dagger.hilt.android.AndroidEntryPoint

const val NOTI_LATER = "noti_later"
const val NOTI_COMPLETE = "noti_complete"

@AndroidEntryPoint
class FeedFragment : Fragment() {
    private val TAG = javaClass.name
    private val plantsViewModel: PlantsViewModel by viewModels()
    private val feedViewModel: FeedViewModel by viewModels()
    private lateinit var binding : FragmentFeedBinding
    private lateinit var plantAdapter: PlantAdapter
//    private lateinit var feedAdapter: FeedAdapter
    private lateinit var feedAdapter: FeedTabAdapter
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
        //feedViewModel.loadData(null,null)
        feedViewModel.feedTabList()
    }

    private fun initList() {
        plantAdapter = PlantAdapter()
        plantAdapter.setType("FEED_PLANT")
        plantAdapter.onItemClicked = { plantId ->

        }
//        feedAdapter = FeedAdapter()
//        feedAdapter.onItemClicked = { feedId ->
//            val bundle = Bundle()
//            bundle.putString("id", feedId)
//            navController.navigate(R.id.view_pager_fragment_to_feed_detail_fragment, bundle)
//        }
        feedAdapter = FeedTabAdapter()
        feedAdapter.onItemClicked = { feedId ->
            Log.i(TAG, "feed id in fragment >> $feedId")
            when(feedId){
                NOTI_LATER -> {}
                NOTI_COMPLETE-> {}
                else -> {
                    val bundle = Bundle()
                    bundle.putString("id", feedId)
                    navController.navigate(R.id.view_pager_fragment_to_feed_detail_fragment, bundle)
                }
            }
        }
    }

    private fun observeViewModel() {
        plantsViewModel.plantsList.observe(viewLifecycleOwner, Observer {
            plantAdapter.refreshItems(it)
        })
//        feedViewModel.feedList.observe(viewLifecycleOwner, Observer {
//            feedAdapter.refreshItems(it)
//        })
        feedViewModel.feedTabList.observe(viewLifecycleOwner, Observer {
            feedAdapter.refreshItems(it)
        })
    }
}
