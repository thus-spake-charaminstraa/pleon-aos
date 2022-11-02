package com.charaminstra.pleon.feed_common

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.charaminstra.pleon.common.CLASS_NAME
import com.charaminstra.pleon.common.FEED_ITEM_CLICK
import com.charaminstra.pleon.feed_common.databinding.ItemFeedDoctorBinding
import com.charaminstra.pleon.common.FeedViewObject
import com.charaminstra.pleon.feed.view.FeedFragmentDirections
import com.google.firebase.analytics.FirebaseAnalytics

class FeedDoctorItemViewHolder(
    private val binding: ItemFeedDoctorBinding
): FeedCommonViewHolder(binding){
    private val TAG = javaClass.name
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    init {
        binding.setClickListener {
            binding.feed?.id?.let { feedId ->
                navigateToFeedDetail(feedId, it)
            }
        }
    }

    private fun navigateToFeedDetail(
        feedId: String,
        view: View
    ) {
        val direction =
            FeedFragmentDirections.feedFragmentToFeedDoctorDetailFragment(
                feedId
            )
        view.findNavController().navigate(direction)
    }

    override fun bind(item: FeedViewObject?)  {
        binding.apply {
            feed = item
            executePendingBindings()
        }
        //binding.plantTagTv.text = binding.root.context.resources.getString(R.string.plant_tag)+item?.plant?.name!!

        var symptomList = ""
        for (i in item?.symptoms?.indices!!){
            if(i == item?.symptoms?.size?.minus(1)){
                symptomList += item?.symptoms?.get(i)?.symptom_ko
            }else{
                symptomList += item?.symptoms?.get(i)?.symptom_ko+"    "
            }
        }
        binding.symptomTxt.text = symptomList

        var solutionList = ""
        for (i in item?.causes?.indices!!){
            if(i == item.causes?.size?.minus(1)){
                solutionList += item?.causes?.get(i)?.guide
            }else{
                solutionList += item?.causes?.get(i)?.guide+"\n"
            }

        }
        binding.solutionTxt.text = solutionList
    }
}
