package com.charaminstra.pleon.feed.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.charaminstra.pleon.common.*
import com.charaminstra.pleon.common_ui.ErrorToast
import com.charaminstra.pleon.common_ui.PLeonNotiDialog
import com.charaminstra.pleon.feed.FeedPlantAdapter
import com.charaminstra.pleon.feed.viewmodel.FeedViewModel
import com.charaminstra.pleon.feed.R
import com.charaminstra.pleon.feed.databinding.FragmentFeedBinding
import com.charaminstra.pleon.feed.guide.NOTI_COMPLETE
import com.charaminstra.pleon.feed.guide.NOTI_GO
import com.charaminstra.pleon.feed.guide.NOTI_LATER
import com.charaminstra.pleon.feed.guide.GuideAdapter
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : Fragment() {
    private val TAG = javaClass.name
    private lateinit var binding : FragmentFeedBinding

    private lateinit var feedPlantAdapter: FeedPlantAdapter
    private lateinit var notiAdapter: GuideAdapter
    private lateinit var feedAdapter: com.charaminstra.pleon.feed_common.FeedAdapter

    private val plantsViewModel: PlantsViewModel by viewModels()
    private val feedViewModel: FeedViewModel by viewModels()
    val pageSnap= PagerSnapHelper()

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    lateinit var navController: NavController
    lateinit var notiDialog : PLeonNotiDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAnalytics= FirebaseAnalytics.getInstance(requireContext())
        navController = findNavController()

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
        notiDialog = PLeonNotiDialog(requireContext())

        initList()
        initListeners()
        observeViewModel()

        feedViewModel.getNotiDialog()

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

                navController.navigate(com.charaminstra.pleon.feed_common.R.id.feed_fragment_to_feed_write_fragment)
            }
        }

        //noti dialog
        notiDialog.setOnGoBtnClickedListener {
            if(plantsViewModel.plantsCount.value == 0){
                ErrorToast(requireContext()).showMsgCenter(resources.getString(R.string.feed_write_error_msg))
            }else{
                navController.navigate(com.charaminstra.pleon.feed_common.R.id.feed_fragment_to_feed_write_fragment)
            }
        }
        notiDialog.setOnTodayStopClickedListener {
            feedViewModel.postNotiTodayStop()
        }

        binding.feedRecyclerview.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

        initScrollListener()

        binding.notiListBtn.setOnClickListener{
            navController.navigate(com.charaminstra.pleon.feed_common.R.id.feed_fragment_to_noti_fragment)

            // logging
            val bundle = Bundle()
            bundle.putString(CLASS_NAME, TAG)
            firebaseAnalytics.logEvent(NOTI_LIST_BTN_CLICK, bundle)
        }
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
        feedPlantAdapter.onItemClicked = { plantId, name ->
            binding.allFilter.isSelected = false
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
        feedAdapter.fromView = "FEED"
        notiAdapter = GuideAdapter()
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

                    binding.celebrate.playAnimation()
                    val vibrator = requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    vibrator.vibrate(100) // 200 ms

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

    override fun onPause() {
        super.onPause()
        binding.celebrate.cancelAnimation()
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
        plantsViewModel.plantsCount.observe(viewLifecycleOwner, Observer {
            if(it==0){
                binding.filterScroll.isVisible = false
            }else{
                binding.filterScroll.isVisible = true
            }
        })
        feedViewModel.feedCount.observe(viewLifecycleOwner, Observer {
            if(it==0){
                binding.noFeedMsg.visibility = View.VISIBLE
            }else{
                binding.noFeedMsg.visibility = View.GONE
            }
        })
        feedViewModel.notiDialogIsExist.observe(viewLifecycleOwner, Observer {
            if(it){
                notiDialog.start(feedViewModel.notiDialogTitle!!,
                    feedViewModel.notiDialogContent!!,
                    feedViewModel.notiDialogButton!!)
            }
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