package com.charaminstra.pleon.feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.charaminstra.pleon.common.PlantsViewModel
import com.charaminstra.pleon.feed.databinding.FragmentFeedBinding
import com.charaminstra.pleon.feed.noti.NOTI_COMPLETE
import com.charaminstra.pleon.feed.noti.NOTI_LATER
import com.charaminstra.pleon.feed.noti.NotiAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : Fragment() {
    private lateinit var binding : FragmentFeedBinding

    private lateinit var feedPlantAdapter: FeedPlantAdapter
    private lateinit var notiAdapter: NotiAdapter
    private lateinit var feedAdapter: com.charaminstra.pleon.feed_common.FeedAdapter

    private val plantsViewModel: PlantsViewModel by viewModels()
    private val feedViewModel: FeedViewModel by viewModels()
    val pageSnap= PagerSnapHelper()

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(layoutInflater)
        binding.allFilter.isSelected = true
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
        initList()
        initListeners()
        observeViewModel()

        //noti recyclervieew
        binding.notiRecyclerview.adapter = notiAdapter


        binding.feedFilterRecyclerview.adapter = feedPlantAdapter
        binding.feedRecyclerview.adapter = feedAdapter
        binding.feedAddBtn.setOnClickListener {
            navController.navigate(R.id.feed_fragment_to_feed_write_fragment)
        }
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
        feedViewModel.getFeedAllList()
        feedViewModel.getNotiList()
    }

    private fun initList() {
        feedPlantAdapter = FeedPlantAdapter()
        feedPlantAdapter.selectedPosition = -1
        feedPlantAdapter.onItemClicked = { plantId ->
            feedViewModel.getFeedFilterList(plantId)
        }
        feedAdapter = com.charaminstra.pleon.feed_common.FeedAdapter()
        notiAdapter = NotiAdapter()
        feedAdapter.onClickFeed = { Id ->
            val bundle = Bundle()
            bundle.putString("id", Id)
            navController.navigate(R.id.feed_fragment_to_feed_detail_fragment, bundle)
        }
        notiAdapter.onClickNoti = { notiId, button ->
            when(button){
                NOTI_LATER -> {
                    feedViewModel.postNotiClick(notiId, "later")
                }
                NOTI_COMPLETE -> {
                    feedViewModel.postNotiClick(notiId, "complete")
                }
                else -> { }
            }
        }
    }

    private fun initListeners(){
        binding.allFilter.setOnClickListener {
            feedViewModel.getFeedAllList()
        }
    }

    private fun observeViewModel() {
        plantsViewModel.plantsList.observe(viewLifecycleOwner, Observer {
            feedPlantAdapter.refreshItems(it)
        })
        feedViewModel.feedAllList.observe(viewLifecycleOwner, Observer {
            binding.allFilter.isSelected=true
            feedPlantAdapter.refreshClick()
            feedAdapter.refreshItems(it)
        })
        feedViewModel.feedFilterList.observe(viewLifecycleOwner, Observer {
            binding.allFilter.isSelected=false
            feedAdapter.refreshItems(it)
        })
        feedViewModel.notiList.observe(viewLifecycleOwner, Observer {
            notiAdapter.refreshItems(it)
            // add page Snap
            pageSnap.attachToRecyclerView(binding.notiRecyclerview)
            binding.notiIndicator.attachToRecyclerView(binding.notiRecyclerview,pageSnap)
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