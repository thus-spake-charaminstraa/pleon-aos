package com.charaminstra.pleon.feed_common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.common.CLASS_NAME
import com.charaminstra.pleon.common.FEED_DOCTOR_DETAIL_VIEW
import com.charaminstra.pleon.common_ui.DateUtils
import com.charaminstra.pleon.feed_common.databinding.FragmentFeedDoctorDetailBinding
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedDoctorDetailFragment : Fragment() {
    private val TAG = javaClass.name

    private val viewModel: FeedDoctorDetailViewModel by viewModels()
    private lateinit var binding : FragmentFeedDoctorDetailBinding
    private lateinit var causeAdapter: CauseAdapter
    private lateinit var solutionAdapter: SolutionAdapter
    private lateinit var symptomAdapter: SymptomAdapter
    lateinit var navController: NavController

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics= FirebaseAnalytics.getInstance(requireContext())

        // logging
        val bundle = Bundle()
        bundle.putString(CLASS_NAME, TAG)
        firebaseAnalytics.logEvent(FEED_DOCTOR_DETAIL_VIEW, bundle)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedDoctorDetailBinding.inflate(inflater, container, false)
        navController = this.findNavController()
        binding.feedDetailBackBtn.setOnClickListener {
            navController.popBackStack()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        initObservers()
        binding.feedCauseRecyclerview.adapter = causeAdapter
        binding.feedSolutionRecyclerview.adapter = solutionAdapter
        binding.feedSymptomRecyclerview.adapter = symptomAdapter

    }
    private fun initList(){
        causeAdapter = CauseAdapter()
        solutionAdapter = SolutionAdapter()
        symptomAdapter = SymptomAdapter()
    }

    private fun initObservers(){
        viewModel.causesList.observe(viewLifecycleOwner){
            causeAdapter.setItemList(it!!)
            solutionAdapter.setItemList(it)
        }
        viewModel.symptomsList.observe(viewLifecycleOwner){
            symptomAdapter.setItemList(it!!)
        }
        viewModel.date.observe(viewLifecycleOwner){
            binding.feedDate.text = DateUtils(requireContext()).dateToView(it!!)
        }
        viewModel.plantName.observe(viewLifecycleOwner){
            binding.plantTagTv.text = resources.getString(R.string.plant_tag) + it
        }
    }
}