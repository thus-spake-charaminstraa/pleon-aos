package com.charaminstra.pleon.feed.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Vibrator
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
import com.charaminstra.pleon.common_ui.PLeonNotiDialog
import com.charaminstra.pleon.feed_common.PlantsViewModel
import com.charaminstra.pleon.feed.FeedPlantAdapter
import com.charaminstra.pleon.feed.viewmodel.FeedViewModel
import com.charaminstra.pleon.feed.R
import com.charaminstra.pleon.feed.databinding.FragmentFeedBinding
import com.charaminstra.pleon.feed.guide.NOTI_COMPLETE
import com.charaminstra.pleon.feed.guide.NOTI_GO
import com.charaminstra.pleon.feed.guide.NOTI_LATER
import com.charaminstra.pleon.feed.guide.GuideAdapter
import com.charaminstra.pleon.feed_common.FeedAdapter
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : Fragment() {
    private val TAG = javaClass.name
    private lateinit var binding : FragmentFeedBinding

    private lateinit var feedPlantAdapter: FeedPlantAdapter
    private lateinit var guideAdapter: GuideAdapter
    private lateinit var feedAdapter: FeedAdapter

    private val plantsViewModel: PlantsViewModel by viewModels()
    private val feedViewModel: FeedViewModel by viewModels()
    private val pageSnap= PagerSnapHelper()

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
        //binding.allFilter.isSelected = true
        binding.completeEffect.cancelAnimation()
        binding.laterEffect.cancelAnimation()

        plantsViewModel.loadData()
        feedViewModel.initFeedList()
        feedViewModel.getGuideList()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notiDialog = PLeonNotiDialog(requireContext())

        initList()
        initListeners()
        observeViewModel()

        feedViewModel.getNotiDialog()
        feedViewModel.getNotiExist()

        //guide recyclervieew
        binding.guideRecyclerview.adapter = guideAdapter

        binding.feedFilterRecyclerview.adapter = feedPlantAdapter
        binding.feedRecyclerview.adapter = feedAdapter
        binding.addFeedBtn.setOnClickListener {
            if(plantsViewModel.plantsCount.value == 0){
                ErrorToast(requireContext()).showMsgCenter(resources.getString(R.string.feed_write_error_msg))
            }else{
                // logging
                val bundle = Bundle()
                bundle.putString(CLASS_NAME, TAG)
                firebaseAnalytics.logEvent(FEED_WRITE_BTN_CLICK, bundle)

                navigateToFeedWritePage()
            }
        }

        //noti event dialog
        notiDialog.setOnGoBtnClickedListener {
            // logging
            val bundle = Bundle()
            bundle.putString(CLASS_NAME, TAG)
            firebaseAnalytics.logEvent(NOTI_DIALOG_GO_BTN_CLICK, bundle)

            if(plantsViewModel.plantsCount.value == 0){
                ErrorToast(requireContext()).showMsgCenter(resources.getString(R.string.feed_write_error_msg))
            }else{
                navigateToFeedWritePage()
            }
        }
        notiDialog.setOnTodayStopClickedListener {
            feedViewModel.postNotiDialogTodayStop()

            // logging
            val bundle = Bundle()
            bundle.putString(CLASS_NAME, TAG)
            firebaseAnalytics.logEvent(NOTI_DIALOG_TODAY_STOP, bundle)
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
    }

    private fun initScrollListener(){
        binding.scroll.setOnScrollChangeListener { v, a, b, c, d ->
            if(!binding.scroll.canScrollVertically(1) and (feedViewModel.isLast.value == false)){
                feedViewModel.nextFeedList()
            }
        }
    }

    private fun initList() {
        feedPlantAdapter = FeedPlantAdapter()
        feedPlantAdapter.onItemClicked = { plantId, name ->
            binding.allFilter.isSelected = false
            feedViewModel.plantId = plantId
            feedViewModel.initFeedList()

            // logging
            val loggingBundle = Bundle()
            loggingBundle.putString(CLASS_NAME, TAG)
            firebaseAnalytics.logEvent(FEED_FILTER_BTN_CLICK , loggingBundle)
        }
        feedAdapter = FeedAdapter()
        feedAdapter.fromView = "FEED"
        guideAdapter = GuideAdapter()
        guideAdapter.onClickNoti = { guideId, button ->
            when(button){
                NOTI_LATER -> {
                    onNotiLater(guideId)
                }
                NOTI_COMPLETE -> {
                    onNotiCompleted(guideId)
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
        binding.completeEffect.cancelAnimation()
        binding.laterEffect.cancelAnimation()
    }

    private fun initListeners(){
        binding.allFilter.setOnClickListener {
            feedViewModel.plantId = null
            feedViewModel.initFeedList()
            binding.allFilter.isSelected=true
            feedPlantAdapter.refreshClick()
        }
    }

    private fun observeViewModel() {
        plantsViewModel.plantsList.observe(viewLifecycleOwner, Observer {
            binding.hasPlant = !it.isNullOrEmpty()
            feedPlantAdapter.refreshItems(it)
        })
        feedViewModel.feedList.observe(viewLifecycleOwner, Observer {
            binding.hasFeed = !it.result.isNullOrEmpty()
            feedAdapter.initItems(it.result!!)
        })
        feedViewModel.guideList.observe(viewLifecycleOwner, Observer {
            guideAdapter.refreshItems(it)
            // add page Snap
            pageSnap.attachToRecyclerView(binding.guideRecyclerview)
            binding.notiIndicator.attachToRecyclerView(binding.guideRecyclerview,pageSnap)
        })
        feedViewModel.notiDialogIsExist.observe(viewLifecycleOwner, Observer {
            if(it!!){
                notiDialog.start(feedViewModel.notiDialogTitle!!,
                    feedViewModel.notiDialogContent!!,
                    feedViewModel.notiDialogButton!!,
                    feedViewModel.notiImgUrl!!)
            }
        })
        feedViewModel.hasNoti.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.hasNoti = it
            }
        })
    }

    private fun navigateToFeedWritePage() {
        navController.navigate(com.charaminstra.pleon.feed_common.R.id.feed_fragment_to_feed_write_fragment)
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

    private fun onNotiCompleted(guideId: String) {
        // logging
        val bundle = Bundle()
        bundle.putString(CLASS_NAME, TAG)
        firebaseAnalytics.logEvent(NOTI_COMPLETE_BTN_CLCIK, bundle)

        binding.completeEffect.playAnimation()
        val vibrator = requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(100) // 200 ms

        feedAdapter.clearItems()
        feedViewModel.postGuideClick(guideId, "COMPLETE")
    }

    private fun onNotiLater(guideId: String) {
        // logging
        val bundle = Bundle()
        bundle.putString(CLASS_NAME, TAG)
        firebaseAnalytics.logEvent(NOTI_LATER_BTN_CLCIK, bundle)

        val vibrator = requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(100) // 200 ms

        binding.laterEffect.visibility = View.VISIBLE
        binding.laterEffect.playAnimation()
        val handler = Handler()
        handler.postDelayed({
            binding.laterEffect.pauseAnimation()
            binding.laterEffect.visibility = View.GONE
        },2000 )

        feedAdapter.clearItems()
        feedViewModel.postGuideClick(guideId, "LATER")
    }
}