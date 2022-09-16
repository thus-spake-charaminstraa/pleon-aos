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
import com.charaminstra.pleon.databinding.FragmentFeedBinding
import com.charaminstra.pleon.plant_register.ui.PlantRegisterActivity
import com.charaminstra.pleon.viewmodel.FeedViewModel
import com.charaminstra.pleon.viewmodel.PlantsViewModel
import dagger.hilt.android.AndroidEntryPoint

const val NOTI_LATER = "noti_later"
const val NOTI_COMPLETE = "noti_complete"

@AndroidEntryPoint
class FeedFragment : Fragment() {
    private val TAG = javaClass.name
    private val plantsViewModel: PlantsViewModel by viewModels()
//    private val feedViewModel: FeedViewModel by viewModels()
    private lateinit var binding : FragmentFeedBinding
    //private lateinit var plantAdapter: PlantAdapter
    //private lateinit var feedAdapter: FeedAdapter
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
        initListeners()
        observeViewModel()
        //binding.feedFilterRecyclerview.adapter = plantAdapter
        //binding.feedRecyclerview.adapter = feedAdapter
        binding.writeBtn.setOnClickListener {
            navController.navigate(R.id.view_pager_fragment_to_feed_write_fragment)
        }
        //binding.feedRecyclerview.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        binding.noPlantButton.setOnClickListener {
            val intent = Intent(context, PlantRegisterActivity::class.java)
            intent.putExtra("from", "main")
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        //viewmodel update
        plantsViewModel.loadData()
        //feedViewModel.getFeedList(null)
    }

    private fun initList() {
        //plantAdapter = PlantAdapter()
//        plantAdapter.setType("FEED_PLANT")
//        plantAdapter.onItemClicked = { plantId ->
//            Log.i(TAG, "plant id in fragment >> $plantId")
//            feedViewModel.getFeedList(plantId)
//        }
//        feedAdapter = FeedAdapter()
//        feedAdapter.onClickFeed = { Id ->
//            Log.i(TAG, "feed id in fragment >> $Id")
//            val bundle = Bundle()
//            bundle.putString("id", Id)
//            navController.navigate(R.id.view_pager_fragment_to_feed_detail_fragment, bundle)
//        }
//        feedAdapter.onClickNoti = {notiId, button ->
//            Log.i(TAG, "noti id in fragment >> $notiId ,, $button")
//            when(button){
//                NOTI_LATER -> {
//                    feedViewModel.postNotiClick(notiId, "later")
//                }
//                NOTI_COMPLETE -> {
//                    feedViewModel.postNotiClick(notiId, "complete")
//                }
//                else -> { }
//            }
//        }
    }

    private fun initListeners(){
//        binding.allFilter.setOnClickListener {
//            feedViewModel.getFeedList(null)
//        }
    }

    private fun observeViewModel() {
//        plantsViewModel.plantsList.observe(viewLifecycleOwner, Observer {
//            plantAdapter.refreshItems(it)
//        })
//        feedViewModel.feedList.observe(viewLifecycleOwner, Observer {
//            feedAdapter.refreshItems(it)
//        })
//        feedViewModel.feedCount.observe(viewLifecycleOwner, Observer{
//            if(it == 0){
//                binding.noFeedTv.visibility = View.VISIBLE
//            }else{
//                binding.noFeedTv.visibility = View.GONE
//            }
//        })
        plantsViewModel.plantsCount.observe(viewLifecycleOwner, Observer{
            if(it == 0) {
                binding.noFeedTv.visibility = View.GONE
                binding.noPlantButton.visibility = View.VISIBLE
            }else{
                binding.noPlantButton.visibility = View.GONE
            }
        })
    }
}
