package com.charaminstra.pleon.feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.charaminstra.pleon.feed_common.FeedViewModel
import com.charaminstra.pleon.common.PlantsViewModel
import com.charaminstra.pleon.feed_common.FeedAdapter
import com.charaminstra.pleon.feed.databinding.FragmentFeedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : Fragment() {
    private lateinit var binding : FragmentFeedBinding
    private lateinit var feedPlantAdapter: FeedPlantAdapter
    private lateinit var feedAdapter: com.charaminstra.pleon.feed_common.FeedAdapter
    private val plantsViewModel: PlantsViewModel by viewModels()
    private val feedViewModel: com.charaminstra.pleon.feed_common.FeedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initListeners()
        observeViewModel()
        binding.feedFilterRecyclerview.adapter = feedPlantAdapter
        binding.feedRecyclerview.adapter = feedAdapter
//        binding.writeBtn.setOnClickListener {
//            navController.navigate(R.id.view_pager_fragment_to_feed_write_fragment)
//        }
        binding.feedRecyclerview.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
//        binding.noPlantButton.setOnClickListener {
//            val intent = Intent(context, PlantRegisterActivity::class.java)
//            intent.putExtra("from", "main")
//            startActivity(intent)
//        }
    }

    override fun onResume() {
        super.onResume()
        //viewmodel update
        plantsViewModel.loadData()
        feedViewModel.getFeedList(null)
    }

    private fun initList() {
        feedPlantAdapter = FeedPlantAdapter()
//        feedPlantAdapter.setType("FEED_PLANT")
        feedPlantAdapter.onItemClicked = { plantId ->
            feedViewModel.getFeedList(plantId)
        }
        feedAdapter = com.charaminstra.pleon.feed_common.FeedAdapter()
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
        binding.allFilter.setOnClickListener {
            feedViewModel.getFeedList(null)
        }
    }

    private fun observeViewModel() {
        plantsViewModel.plantsList.observe(viewLifecycleOwner, Observer {
            feedPlantAdapter.refreshItems(it)
        })
        feedViewModel.feedList.observe(viewLifecycleOwner, Observer {
            feedAdapter.refreshItems(it)
        })
//        feedViewModel.feedCount.observe(viewLifecycleOwner, Observer{
//            if(it == 0){
//                binding.noFeedTv.visibility = View.VISIBLE
//            }else{
//                binding.noFeedTv.visibility = View.GONE
//            }
//        })
//        plantsViewModel.plantsCount.observe(viewLifecycleOwner, Observer{
//            if(it == 0) {
//                binding.noFeedTv.visibility = View.GONE
//                binding.noPlantButton.visibility = View.VISIBLE
//            }else{
//                binding.noPlantButton.visibility = View.GONE
//            }
//        })
    }
}