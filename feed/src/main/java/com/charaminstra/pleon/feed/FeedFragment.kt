package com.charaminstra.pleon.feed

import android.content.Context
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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.charaminstra.pleon.common.*
import com.charaminstra.pleon.common_ui.ErrorToast
import com.charaminstra.pleon.feed.databinding.FragmentFeedBinding
import com.charaminstra.pleon.feed.noti.NOTI_COMPLETE
import com.charaminstra.pleon.feed.noti.NOTI_GO
import com.charaminstra.pleon.feed.noti.NOTI_LATER
import com.charaminstra.pleon.feed.noti.NotiAdapter
import com.charaminstra.pleon.feed_common.FeedViewType
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : Fragment() {
    private val TAG = javaClass.name
    private lateinit var binding : FragmentFeedBinding

    private lateinit var feedPlantAdapter: FeedPlantAdapter
    private lateinit var notiAdapter: NotiAdapter
    private lateinit var feedAdapter: com.charaminstra.pleon.feed_common.FeedAdapter

    private val plantsViewModel: PlantsViewModel by viewModels()
    private val feedViewModel: FeedViewModel by viewModels()
    val pageSnap= PagerSnapHelper()

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAnalytics= FirebaseAnalytics.getInstance(requireContext())

        // logging
        val bundle = Bundle()
        bundle.putString(CLASS_NAME, TAG)
        firebaseAnalytics.logEvent(FEED_VIEW, bundle)
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
            if(plantsViewModel.plantsCount.value == 0){
                ErrorToast(requireContext()).showMsgCenter(resources.getString(R.string.feed_write_error_msg))
            }else{
                // logging
                val bundle = Bundle()
                bundle.putString(CLASS_NAME, TAG)
                firebaseAnalytics.logEvent(FEED_WRITE_BTN_CLICK, bundle)

                navController.navigate(R.id.feed_fragment_to_feed_write_fragment)
            }
        }
        binding.feedRecyclerview.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

        initScrollListener()
    }

    override fun onResume() {
        super.onResume()
        //viewmodel update
        plantsViewModel.loadData()
        feedViewModel.offset = 0
        feedViewModel.plantId = null
        feedAdapter.clearItems()
        feedViewModel.getFeedAllList()
        feedViewModel.getNotiList()
    }

    private fun initScrollListener(){
        binding.scroll.setOnScrollChangeListener { v, a, b, c, d ->
            if(!binding.scroll.canScrollVertically(1)){
                Log.i(TAG,"delete!!!")
                feedAdapter.deleteLoading()
                feedViewModel.getFeedAllList()
            }
        }
    }

    private fun initList() {
        feedPlantAdapter = FeedPlantAdapter()
        feedPlantAdapter.selectedPosition = -1
        feedPlantAdapter.onItemClicked = { plantId ->
            binding.allFilter.isSelected=false
            feedViewModel.offset = 0
            feedAdapter.viewItemList.clear()
            feedViewModel.plantId = plantId
            feedViewModel.getFeedAllList()

            // logging
            val loggingBundle = Bundle()
            loggingBundle.putString(CLASS_NAME, TAG)
            firebaseAnalytics.logEvent(FEED_FILTER_BTN_CLICK , loggingBundle)
        }
        feedAdapter = com.charaminstra.pleon.feed_common.FeedAdapter()
        notiAdapter = NotiAdapter()
        feedAdapter.onClickFeed = { ViewType, Id ->
            if(ViewType == FeedViewType.FEED.ordinal){
                val bundle = Bundle()
                bundle.putString("id", Id)
                navController.navigate(R.id.feed_fragment_to_feed_detail_fragment, bundle)
            }else if(ViewType == FeedViewType.DIAGNOSIS.ordinal){
                val bundle = Bundle()
                bundle.putString("id", Id)
                navController.navigate(R.id.feed_fragment_to_feed_doctor_detail_fragment, bundle)
            }

            // logging
            val loggingBundle = Bundle()
            loggingBundle.putString(CLASS_NAME, TAG)
            firebaseAnalytics.logEvent(FEED_ITEM_CLICK , loggingBundle)
        }
        notiAdapter.onClickNoti = { notiId, button ->
            when(button){
                NOTI_LATER -> {
                    // logging
                    val bundle = Bundle()
                    bundle.putString(CLASS_NAME, TAG)
                    firebaseAnalytics.logEvent(NOTI_LATER_BTN_CLCIK, bundle)

                    feedAdapter.clearItems()
                    feedViewModel.postNotiClick(notiId, "LATER")
                }
                NOTI_COMPLETE -> {
                    // logging
                    val bundle = Bundle()
                    bundle.putString(CLASS_NAME, TAG)
                    firebaseAnalytics.logEvent(NOTI_COMPLETE_BTN_CLCIK, bundle)

                    feedAdapter.clearItems()
                    feedViewModel.postNotiClick(notiId, "COMPLETE")
                }
                NOTI_GO -> {
                    startPlantRegisterActivity(requireContext())
                }
                else -> { }
            }
        }
    }

    private fun initListeners(){
        binding.allFilter.setOnClickListener {
            feedViewModel.plantId = null
            feedViewModel.offset = 0
            feedAdapter.viewItemList.clear()
            feedViewModel.getFeedAllList()
            binding.allFilter.isSelected=true
            feedPlantAdapter.refreshClick()
        }
    }

    private fun observeViewModel() {
        plantsViewModel.plantsList.observe(viewLifecycleOwner, Observer {
            feedPlantAdapter.refreshItems(it)
        })
        feedViewModel.feedAllList.observe(viewLifecycleOwner, Observer {
            if(feedViewModel.isLast.value == false){
                feedAdapter.addItemsAndLoading(it)
            }else{
                feedAdapter.addFinalItems(it)
            }
        })
        feedViewModel.notiList.observe(viewLifecycleOwner, Observer {
            notiAdapter.refreshItems(it)
            // add page Snap
            pageSnap.attachToRecyclerView(binding.notiRecyclerview)
            binding.notiIndicator.attachToRecyclerView(binding.notiRecyclerview,pageSnap)
        })
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