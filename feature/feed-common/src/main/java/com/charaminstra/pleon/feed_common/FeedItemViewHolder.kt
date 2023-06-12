package com.charaminstra.pleon.feed_common

import android.view.View
import androidx.navigation.findNavController
import com.charaminstra.pleon.feed_common.databinding.ItemFeedBinding
import com.charaminstra.pleon.common.FeedViewObject
import com.charaminstra.pleon.garden.ui.PlantDetailFragmentDirections
import com.charaminstra.pleon.feed.view.FeedFragmentDirections
import com.google.firebase.analytics.FirebaseAnalytics

class FeedItemViewHolder(
    private val binding: ItemFeedBinding
): FeedCommonViewHolder(binding){
    private val TAG = javaClass.name
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var fromView : String

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
        when(fromView){
            "FEED" -> {
                val direction = FeedFragmentDirections.feedFragmentToFeedDetailFragment(
                    feedId
                )
                view.findNavController().navigate(direction)
            }
            "GARDEN" -> {
                val direction = PlantDetailFragmentDirections.plantDetailFragmentToFeedDetailFragment(
                    feedId
                )
                view.findNavController().navigate(direction)
            }
        }
    }

    override fun bind(item: FeedViewObject?, fromView: String)  {
        this.fromView = fromView
        binding.apply {
            feed = item
            executePendingBindings()
        }
        if(item?.image_url != null) {
            binding.plantImage.visibility = View.VISIBLE
        }else{
            binding.plantImage.visibility = View.GONE
        }

        //comment
        val count = item?.comments?.size
        if(count == 0) {
            binding.icComment.visibility = View.GONE
            binding.feedCommentCount.visibility = View.GONE
        }else{
            binding.icComment.visibility = View.VISIBLE
            binding.feedCommentCount.visibility = View.VISIBLE
            binding.feedCommentCount.text = count.toString()
        }
    }
}
